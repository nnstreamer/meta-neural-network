SUMMARY = "NNStreamer, Stream Pipeline Paradigm for Nerual Network Applications"
DESCRIPTION = "NNStreamer is a GStreamer plugin allowing to construct neural network applications with stream pipeline paradigm."
HOMEPAGE = "https://github.com/nnsuite/nnstreamer"
BUGTRACKER = "https://github.com/nnsuite/nnstreamer/issues"
SECTION = "AI"
LICENSE = "LGPLv2+"

DEPENDS = "glib-2.0 gstreamer1.0"

SRC_URI = "https://github.com/nnsuite/nnstreamer.git"
SRC_REV = "HEAD"

S = "${WORKDIR}/nnstreamer.git"

RDEPENDS_${PN} = "base-files"

inherit meson

FILES_${PN} += "${libdir}/*.so \
	${libdir}/gstreamer-1.0/*.so"
