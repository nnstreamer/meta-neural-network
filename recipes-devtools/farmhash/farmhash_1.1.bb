DESCRIPTION = "FarmHash, a family of hash functions"
AUTHOR = "Google Inc."
HOMEPAGE = "https://github.com/google/farmhash"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=7dfaa79e2b070897e495fec386e3acfc"
PR = "git20170913.816a4ae"

SRC_URI = "git://github.com/google/farmhash.git"
SRCREV = "816a4ae622e964763ca0862d9dbd19324a1eaf45"

S = "${WORKDIR}/git"

inherit autotools

FILES_${PN} = "${libdir}/*.so.*"
FILES_${PN}-dev = "${includedir}/*.h \
    ${libdir}/*.a \
    ${libdir}/*so"
FILES_${PN}-doc = "${datadir}/doc/${PN}/*"

PROVIDES += "tflite-1.12-build-dep-farmhash"
