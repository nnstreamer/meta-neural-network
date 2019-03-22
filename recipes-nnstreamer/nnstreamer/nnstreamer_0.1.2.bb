SUMMARY = "NNStreamer, Stream Pipeline Paradigm for Nerual Network Applications"
DESCRIPTION = "NNStreamer is a GStreamer plugin allowing to construct neural network applications with stream pipeline paradigm."
SECTION = "AI"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a6f89e2100d9b6cdffcea4f398e37343 \
                    file://tizen-api/LICENSE.Apache-2.0;md5=d3ae35440dea13087a83d59100346a44 \
                    file://debian/copyright;md5=0462ef8fa89a1f53f2e65e74940519ef"

DEPENDS = "glib-2.0 gstreamer1.0 gstreamer1.0-plugins-base gtest sysvinit"
SRC_URI = "git://github.com/nnsuite/nnstreamer.git;protocol=https"

PV = "0.1.2+git${SRCPV}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit meson pkgconfig

EXTRA_OEMESON += "-Denable-test=true \
	          -Denable-opencv-test=false \
		  -Denable-tensorflow-lite=false \
		  -Denable-tensorflow=false \
		  -Denable-tensorflow-mem-optmz=false \
		  -Dinstall-example=true \
		  -Ddisable-audio-support=false \
		  "

FILES_${PN} += "${libdir}/*.so \
	    ${libdir}/gstreamer-1.0/*.so \
	    ${libdir}/nnstreamer/* \
	    ${sysconfdir}/nnstreamer.ini"

FILES_${PN}-dev += "${includedir}/nnstreamer/* \
		${libdir}/*.a \
		${libdir}/pkgconfig/nnstreamer.pc"

PACKAGES =+ "${PN}-unittest"

FILES_${PN}-unittest = "${libdir}/${PN}/unittest/*"

