SUMMARY = "NNStreamer, Stream Pipeline Paradigm for Nerual Network Applications"
DESCRIPTION = "NNStreamer is a GStreamer plugin allowing to construct neural network applications with stream pipeline paradigm."
SECTION = "AI"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "\
                file://LICENSE;md5=c25e5c1949624d71896127788f1ba590 \
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
            json-glib \
            "
DEPENDS += "\
        ${@bb.utils.contains('DISTRO_FEATURES','tensorflow-lite','tensorflow-lite','',d)} \
        "
SRC_URI = "\
        git://github.com/nnstreamer/nnstreamer.git;branch=main;protocol=https \
        "

PV = "2.1.0+git${SRCPV}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit meson pkgconfig

EXTRA_OEMESON += "\
                -Denable-test=true \
                -Dinstall-test=true \
                -Dskip-tflite-flatbuf-check=true \
                -Denable-pbtxt-converter=false \
                "

PACKAGECONFIG ??= "\
                ${@bb.utils.contains('DISTRO_FEATURES','opencv','opencv','',d)} \
                ${@bb.utils.contains('DISTRO_FEATURES','tensorflow','tensorflow','',d)} \
                "

do_install:append() {
   rm -f ${D}/${bindir}/unittest-nnstreamer/tests/test_models/models/tvm*
   cd ${D}/${libdir}
   ln -sf ./gstreamer-1.0/libnnstreamer.so ./libnnstreamer.so
}
INSANE_SKIP:${PN} += "dev-so"

FILES:${PN} += "\
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

FILES:${PN}-unittest += "\
                    ${libdir}/nnstreamer/customfilters/* \
                    ${bindir}/unittest-nnstreamer/* \
                    "

FILES:${PN}-tensorflow-lite += "\
                                ${@bb.utils.contains('DISTRO_FEATURES','tensorflow-lite', \
                                    '${libdir}/nnstreamer/filters/libnnstreamer_filter_tensorflow2-lite.so', \
                                    '', d)} \
                                "
RPROVIDES:${PN}-tensorflow-lite = "${libdir}/nnstreamer/filters/libnnstreamer_filter_tensorflow2-lite.so"

RDEPENDS:${PN}-unittest = "nnstreamer gstreamer1.0-plugins-good ssat"
RDEPENDS:${PN}-unittest += "\
                            ${@bb.utils.contains( \
                                'DISTRO_FEATURES','tensorflow-lite', \
                                '${libdir}/nnstreamer/filters/libnnstreamer_filter_tensorflow2-lite.so', \
                                '', d)} \
                            "

RDEPENDS:${PN} = "\
                glib-2.0 \
                json-glib \
                gstreamer1.0 \
                gstreamer1.0-plugins-base \
                python3 \
                python3-numpy \
                python3-math \
                "

FILES:${PN}-dev = "\
                ${includedir}/nnstreamer/* \
                ${libdir}/*.a \
                ${libdir}/pkgconfig/*.pc \
                "
