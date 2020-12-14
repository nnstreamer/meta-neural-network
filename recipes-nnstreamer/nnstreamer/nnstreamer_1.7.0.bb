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
        git://github.com/nnstreamer/nnstreamer.git;branch=main;protocol=https \
        file://0001-Disable-flatbuf-check-for-tf-lite-for-yocto-build.patch \
        "

PV = "1.7.0+git${SRCPV}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit meson pkgconfig

EXTRA_OEMESON += "\
                -Denable-test=true \
                -Dinstall-test=true \
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
                                    '${libdir}/nnstreamer/filters/libnnstreamer_filter_tensorflow1-lite.so', \
                                    '', d)} \
                                "
RPROVIDES_${PN}-tensorflow-lite = "${libdir}/nnstreamer/filters/libnnstreamer_filter_tensorflow1-lite.so"

RDEPENDS_${PN}-unittest = "nnstreamer gstreamer1.0-plugins-good ssat"
RDEPENDS_${PN}-unittest += "\
                            ${@bb.utils.contains( \
                                'DISTRO_FEATURES','tensorflow-lite', \
                                '${libdir}/nnstreamer/filters/libnnstreamer_filter_tensorflow1-lite.so', \
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
