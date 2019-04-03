SUMMARY = "NNStreamer, Stream Pipeline Paradigm for Nerual Network Applications"
DESCRIPTION = "NNStreamer is a GStreamer plugin allowing to construct neural network applications with stream pipeline paradigm."
SECTION = "AI"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a6f89e2100d9b6cdffcea4f398e37343 \
                    file://tizen-api/LICENSE.Apache-2.0;md5=d3ae35440dea13087a83d59100346a44 \
                    file://debian/copyright;md5=0462ef8fa89a1f53f2e65e74940519ef"

DEPENDS = "glib-2.0 gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-good gtest ssat"

SRC_URI = "git://github.com/nnsuite/nnstreamer.git;protocol=https"

PV = "0.1.2+git${SRCPV}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit meson pkgconfig

EXTRA_OEMESON += "-Denable-test=${INSTALL_TEST} \
	          -Dinstall-test=${INSTALL_TEST} \
		  -Denable-tensorflow-mem-optmz=false \
		  -Dinstall-example=${INSTALL_TEST} \
		  -Ddisable-audio-support=false \
		  "

PACKAGECONFIG ??= "${@bb.utils.contains('DISTRO_FEATURES','opencv','opencv','',d)} ${@bb.utils.contains('DISTRO_FEATURES','tensorflow','tensorflow','',d)} ${@bb.utils.contains('DISTRO_FEATURES','tensorflow-lite','tensorflow-lite','',d)}"

PACKAGECONFIG[opencv] = "-Denable-opencv-test=true,-Denable-opencv-test=false,opencv"
PACKAGECONFIG[tensorflow] = "-Denable-tensorflow=true,-Denable-tensorflow=false,tensorflow"
PACKAGECONFIG[tensorflow-lite] = "-Denable-tensorflow-lite=true,-Denable-tensorflow-lite=false,tensorflow-lite"

FILES_${PN} += "${libdir}/*.so \
	    ${libdir}/gstreamer-1.0/*.so \
	    ${libdir}/nnstreamer/* \
	    ${sysconfdir}/nnstreamer.ini "

RDEPENDS_${PN} = "python3-numpy"

FILES_${PN}-dev += "${includedir}/nnstreamer/* \
		${libdir}/*.a \
		${libdir}/pkgconfig/nnstreamer.pc"
