SUMMARY = "NNStreamer, Stream Pipeline Paradigm for Nerual Network Applications"
DESCRIPTION = "NNStreamer is a GStreamer plugin allowing to construct neural network applications with stream pipeline paradigm."
SECTION = "AI"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "\
                file://LICENSE;md5=a6f89e2100d9b6cdffcea4f398e37343 \
                file://debian/copyright;md5=0462ef8fa89a1f53f2e65e74940519ef \
                "

DEPENDS = "glib-2.0 gstreamer1.0 gstreamer1.0-plugins-base gtest tensorflow-lite"

SRC_URI = "git://github.com/nnsuite/nnstreamer.git;protocol=https"

PV = "0.1.2+git${SRCPV}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit meson pkgconfig

EXTRA_OEMESON += "\
                -Denable-test=true \
                -Dinstall-test=true \
                -Denable-tensorflow-lite=true \
                -Denable-tensorflow-mem-optmz=false \
                -Denable-pytorch=false \
                -Dinstall-example=true \
                -Ddisable-audio-support=false \
                "

PACKAGECONFIG ??= "\
                ${@bb.utils.contains('DISTRO_FEATURES','opencv','opencv','',d)} \
                ${@bb.utils.contains('DISTRO_FEATURES','tensorflow','tensorflow','',d)} \
                "

PACKAGECONFIG[opencv] = "-Denable-opencv-test=true,-Denable-opencv-test=false,opencv"
PACKAGECONFIG[tensorflow] = "-Denable-tensorflow=true,-Denable-tensorflow=false,tensorflow"

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

PACKAGES =+ "${PN}-unittest ${PN}-tensorflow-lite"

FILES_${PN}-unittest += "\
                    ${libdir}/nnstreamer/customfilters/* \
                    ${libdir}/nnstreamer/unittest/* \
                    "
FILES_${PN}-tensorflow-lite += "${libdir}/nnstreamer/filters/libnnstreamer_filter_tensorflow-lite.so"

RDEPENDS_${PN}-unittest = "nnstreamer gstreamer1.0-plugins-good gtest python3-numpy python3-math ssat python-math python-numpy"

RDEPENDS_${PN} = "glib-2.0 gstreamer1.0 gstreamer1.0-plugins-base"

FILES_${PN}-dev += "\
                ${includedir}/nnstreamer/* \
                ${libdir}/*.a \
                ${libdir}/pkgconfig/nnstreamer.pc \
                "
