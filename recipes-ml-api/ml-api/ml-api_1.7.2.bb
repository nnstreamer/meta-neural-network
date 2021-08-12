SUMMARY = "ML(Machine Learning) native API for NNStreamer"
DESCRIPTION = "You can construct a data stream pipeline with neural networks easily."
SECTION = "AI"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "\
    file://LICENSE;md5=095e13fef457e259d3bc155d0ed859f1 \
    file://debian/copyright;md5=0c9b7e46807b3b7b1a591deb1a854c8e \
"

DEPENDS = "\
    glib-2.0 \
    gstreamer1.0 \
    nnstreamer \
"

SRC_URI = "\
    git://github.com/nnstreamer/api.git;branch=main;protocol=https \
"

PV = "1.7.2+git${SRCPV}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit meson pkgconfig

EXTRA_OEMESON += "\
    -Denable-test=true \
    -Dinstall-test=true \
    -Denable-tizen=false \
"

INSANE_SKIP_${PN} += "dev-so"

FILES_${PN} += "\
    ${libdir}/*.so \
"

RDEPENDS_${PN} = "\
    glib-2.0 \
    gstreamer1.0 \
    nnstreamer \
"

FILES_${PN}-dev = "\
    ${includedir}/nnstreamer/*.h \
    ${libdir}/*.a \
    ${libdir}/pkgconfig/*.pc \
"
