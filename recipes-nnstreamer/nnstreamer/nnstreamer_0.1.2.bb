SUMMARY = "NNStreamer, Stream Pipeline Paradigm for Nerual Network Applications"
DESCRIPTION = "NNStreamer is a GStreamer plugin allowing to construct neural network applications with stream pipeline paradigm."
SECTION = "AI"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a6f89e2100d9b6cdffcea4f398e37343 \
                    file://tizen-api/LICENSE.Apache-2.0;md5=d3ae35440dea13087a83d59100346a44 \
                    file://debian/copyright;md5=0462ef8fa89a1f53f2e65e74940519ef"

DEPENDS = "glib-2.0 gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-good gtest"

SRC_URI = "git://github.com/nnsuite/nnstreamer.git;protocol=https"

PV = "0.1.2+git${SRCPV}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit meson pkgconfig

EXTRA_OEMESON += "-Denable-test=true \
	          -Dinstall-test=true \
	          -Denable-opencv-test=false \
		  -Denable-tensorflow-lite=false \
		  -Denable-tensorflow=false \
		  -Denable-tensorflow-mem-optmz=false \
		  -Dinstall-example=true \
		  -Ddisable-audio-support=false \
		  "

do_install_append(){
    install -d ${D}${libdir}/${PN}/unittest/tests/nnstreamer_merge
    install -d ${D}${libdir}/${PN}/unittest/tests/nnstreamer_converter
    install -d ${D}${libdir}/${PN}/unittest/tests/nnstreamer_decoder
    install -d ${D}${libdir}/${PN}/unittest/tests/nnstreamer_demux
    install -d ${D}${libdir}/${PN}/unittest/tests/nnstreamer_filter_custom
    install -d ${D}${libdir}/${PN}/unittest/tests/nnstreamer_mux
    install -d ${D}${libdir}/${PN}/unittest/tests/nnstreamer_repo
    install -d ${D}${libdir}/${PN}/unittest/tests/nnstreamer_repo_dynamicity
    install -d ${D}${libdir}/${PN}/unittest/tests/nnstreamer_repo_lstm
    install -d ${D}${libdir}/${PN}/unittest/tests/nnstreamer_repo_rnn
    install -d ${D}${libdir}/${PN}/unittest/tests/nnstreamer_split
    install -d ${D}${libdir}/${PN}/unittest/tests/transform_arithmetic
    install -d ${D}${libdir}/${PN}/unittest/tests/transform_dimchg
    install -d ${D}${libdir}/${PN}/unittest/tests/transform_stand
    install -d ${D}${libdir}/${PN}/unittest/tests/transform_transpose
    install -d ${D}${libdir}/${PN}/unittest/tests/transform_typecast


    install -m 0644 ${S}/tests/gen24bBMP.py ${D}${libdir}/${PN}/unittest/tests/gen24bBMP.py
    install -m 0644 ${S}/tests/nnstreamer_merge/* ${D}${libdir}/${PN}/unittest/tests/nnstreamer_merge/
    install -m 0644 ${S}/tests/nnstreamer_converter/* ${D}${libdir}/${PN}/unittest/tests/nnstreamer_converter/
    install -m 0644 ${S}/tests/nnstreamer_decoder/* ${D}${libdir}/${PN}/unittest/tests/nnstreamer_decoder/
    install -m 0644 ${S}/tests/nnstreamer_demux/* ${D}${libdir}/${PN}/unittest/tests/nnstreamer_demux/
    install -m 0644 ${S}/tests/nnstreamer_filter_custom/* ${D}${libdir}/${PN}/unittest/tests/nnstreamer_filter_custom/
    install -m 0644 ${S}/tests/nnstreamer_mux/* ${D}${libdir}/${PN}/unittest/tests/nnstreamer_mux/
    install -m 0644 ${S}/tests/nnstreamer_repo/* ${D}${libdir}/${PN}/unittest/tests/nnstreamer_repo/
    install -m 0644 ${S}/tests/nnstreamer_repo_dynamicity/runTest.sh ${D}${libdir}/${PN}/unittest/tests/nnstreamer_repo_dynamicity/
    install -m 0644 ${S}/tests/nnstreamer_repo_lstm/* ${D}${libdir}/${PN}/unittest/tests/nnstreamer_repo_lstm/
    install -m 0644 ${S}/tests/nnstreamer_repo_rnn/* ${D}${libdir}/${PN}/unittest/tests/nnstreamer_repo_rnn/
    install -m 0644 ${S}/tests/nnstreamer_split/* ${D}${libdir}/${PN}/unittest/tests/nnstreamer_split/
    install -m 0644 ${S}/tests/transform_arithmetic/* ${D}${libdir}/${PN}/unittest/tests/transform_arithmetic/
    install -m 0644 ${S}/tests/transform_dimchg/* ${D}${libdir}/${PN}/unittest/tests/transform_dimchg/
    install -m 0644 ${S}/tests/transform_stand/* ${D}${libdir}/${PN}/unittest/tests/transform_stand/
    install -m 0644 ${S}/tests/transform_transpose/* ${D}${libdir}/${PN}/unittest/tests/transform_transpose/
    install -m 0644 ${S}/tests/transform_typecast/* ${D}${libdir}/${PN}/unittest/tests/transform_typecast/
}


FILES_${PN} += "${libdir}/*.so \
	    ${libdir}/gstreamer-1.0/*.so \
	    ${libdir}/nnstreamer/* \
	    ${sysconfdir}/nnstreamer.ini"

FILES_${PN}-dev += "${includedir}/nnstreamer/* \
		${libdir}/*.a \
		${libdir}/pkgconfig/nnstreamer.pc"

PACKAGES =+ "${PN}-unittest"

RDEPENDS_${PN}-unittest = "python3-numpy"

FILES_${PN}-unittest += "${libdir}/${PN}/unittest/*"
