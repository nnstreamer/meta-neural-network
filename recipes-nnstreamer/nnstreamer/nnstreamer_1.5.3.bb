SUMMARY = "NNStreamer, Stream Pipeline Paradigm for Nerual Network Applications"
DESCRIPTION = "NNStreamer is a GStreamer plugin allowing to construct neural network applications with stream pipeline paradigm."
SECTION = "AI"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "\
                file://LICENSE;md5=a6f89e2100d9b6cdffcea4f398e37343 \
                file://debian/copyright;md5=0462ef8fa89a1f53f2e65e74940519ef \
                "

DEPENDS = "\
            orc-native \
            glib-2.0 \
            gstreamer1.0 \
            gstreamer1.0-plugins-base \
            python3 \
            python3-numpy \
            gtest \
            "
DEPENDS += "\
        ${@bb.utils.contains('DISTRO_FEATURES','tensorflow-lite','tensorflow-lite','',d)} \
        "
SRC_URI = "\
        git://github.com/nnsuite/nnstreamer.git;protocol=https \
        file://0001-Test-Common-Remove-a-unit-test-for-custom-configurat.patch \
        "

PV = "1.5.3+git${SRCPV}"
# After 1.6.0 is released, SRCREV will be changed as ${AUTOREV}
SRCREV = "e772917095fa7a700127cdb90846e4e92e22eb48"

S = "${WORKDIR}/git"

inherit meson pkgconfig

EXTRA_OEMESON += "\
                -Denable-test=true \
                -Dinstall-test=true \
                -Dinstall-example=true \
                "

PACKAGECONFIG ??= "\
                ${@bb.utils.contains('DISTRO_FEATURES','opencv','opencv','',d)} \
                ${@bb.utils.contains('DISTRO_FEATURES','tensorflow','tensorflow','',d)} \
                "

do_install_append() {
    (cd ${D}/${libdir}; ln -s ./gstreamer-1.0/libnnstreamer.so)
}
INSANE_SKIP_${PN} += "dev-so"

FILES_${PN} += "\
            ${libdir}/*.so \
            ${libdir}/gstreamer-1.0/*.so \
            ${libdir}/nnstreamer/decoders/* \
            ${sysconfdir}/nnstreamer.ini \
            "

PACKAGES =+ "\
            ${PN}-unittest \
            ${@bb.utils.contains('DISTRO_FEATURES','tensorflow-lite', \
                    '${PN}-tensorflow-lite', \
                    '', d)} \
            "

FILES_${PN}-unittest += "\
                    ${libdir}/nnstreamer/customfilters/* \
                    ${libdir}/nnstreamer/unittest/* \
                    "

FILES_${PN}-tensorflow-lite += "\
                                ${@bb.utils.contains('DISTRO_FEATURES','tensorflow-lite', \
                                    '${libdir}/nnstreamer/filters/libnnstreamer_filter_tensorflow-lite.so', \
                                    '', d)} \
                                "
RPROVIDES_${PN}-tensorflow-lite = "${libdir}/nnstreamer/filters/libnnstreamer_filter_tensorflow-lite.so"

RDEPENDS_${PN}-unittest = "nnstreamer gstreamer1.0-plugins-good ssat"
RDEPENDS_${PN}-unittest += "\
                            ${@bb.utils.contains( \
                                'DISTRO_FEATURES','tensorflow-lite', \
                                '${libdir}/nnstreamer/filters/libnnstreamer_filter_tensorflow-lite.so', \
                                '', d)} \
                            "

RDEPENDS_${PN} = "\
                glib-2.0 \
                gstreamer1.0 \
                gstreamer1.0-plugins-base \
                python3 \
                python3-numpy \
                python3-math \
                "

FILES_${PN}-dev = "\
                ${includedir}/nnstreamer/* \
                ${libdir}/*.a \
                ${libdir}/pkgconfig/*.pc \
                "
