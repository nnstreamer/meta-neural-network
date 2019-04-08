DESCRIPTION = "General Purpose FFT (Fast Fourier/Cosine/Sine Transform) Package"
AUTHOR = "Takuya OOURA"
HOMEPAGE = "http://www.kurims.kyoto-u.ac.jp/~ooura/fft.html"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://readme.txt;md5=0383479ef4df1bbc666ddf614dfaa87f"

SRC_URI = "http://www.kurims.kyoto-u.ac.jp/~ooura/fft.tgz"
SRC_URI[md5sum] = "4255dd8a74949d123216b1ab91520469"
SRC_URI[sha256sum] = "52bb637c70b971958ec79c9c8752b1df5ff0218a4db4510e60826e0cb79b5296"
PR = "200612"

S = "${WORKDIR}/fft"

do_install() {
    install -d ${D}${prefix}/src/${PN}
    install -m 0644 ${S}/*.c ${D}${prefix}/src/${PN}/
    install -m 0644 ${S}/*.f ${D}${prefix}/src/${PN}/
}

PACKAGES = "${PN}-source"
PROVIDES += "tflite-1.12-build-dep-fft"

FILES_${PN}-source = "${prefix}/src/fft/*.c ${prefix}/src/fft/*.f"
