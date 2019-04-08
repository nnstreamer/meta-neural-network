DESCRIPTION = "A small self-contained low-precision GEMM library"
AUTHOR = "Google Inc., Intel Corporation, ARM Ltd., Silk Labs Inc., MIPS Tech LLC, and Wave Computing Inc."
HOMEPAGE = "https://github.com/google/gemmlowp"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI = "git://github.com/google/gemmlowp.git"
SRCREV = "38ebac7b059e84692f53e5938f97a9943c120d98"
PR = "git20190417.38ebac7"

S = "${WORKDIR}/git"
PACKAGES = "${PN}-dev"
PROVIDES += "tflite-1.12-build-dep-${PN}"
RDEPENDS_${PN}-dev = " "

do_install() {
    install -d ${D}${includedir}/gemmlowp/public
    install -m 0644 ${S}/public/*.h ${D}${includedir}/gemmlowp/public/
}

