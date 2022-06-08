DESCRIPTION = "TensorFlow’s lightweight solution for mobile and embedded devices"
AUTHOR = "Google Inc. and Yuan Tang"
HOMEPAGE = "https://www.tensorflow.org/lite"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4158a261ca7f2525513e31ba9c50ae98"

# PV = "2.9.1"
SRCREV = "d8ce9f9c301d021a69953134185ab728c1c248d3"

DEPENDS = "zlib libgfortran"
SRC_URI = " \
    git://github.com/tensorflow/tensorflow.git;branch=r2.9;rev=${SRCREV};protocol=https \
    file://fix-to-cmake-2.9.1.patch \
    file://tensorflow2-lite.pc.in \
"

inherit cmake

S = "${WORKDIR}/git"

EXTRA_OECMAKE = "\
    ${S}/tensorflow/lite/ \
"
EXTRA_OECMAKE:append = "-DFETCHCONTENT_FULLY_DISCONNECTED=OFF"

do_configure[network] =  "1"
do_compile[network] = "1"

do_install() {
    # install libraries
    install -d ${D}${libdir}
    install -m 0644 ${B}/libtensorflow-lite-bundled.a ${D}${libdir}/libtensorflow2-lite.a
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${WORKDIR}/tensorflow2-lite.pc.in ${D}${libdir}/pkgconfig/tensorflow2-lite.pc

    # install header files
    install -d ${D}${includedir}/tensorflow/lite
    cd ${S}/tensorflow/lite
    cp --parents \
        $(find . -name "*.h*") \
        ${D}${includedir}/tensorflow/lite

    # install version.h from core
    install -d ${D}${includedir}/tensorflow/core/public
    cp ${S}/tensorflow/core/public/version.h ${D}${includedir}/tensorflow/core/public

    sed -i 's:@version@:${PV}:g
        s:@libdir@:${libdir}:g
        s:@includedir@:${includedir}:g' ${D}${libdir}/pkgconfig/tensorflow2-lite.pc

    # flatbuffers
    install -d  ${D}${includedir}/flatbuffers
    install -m 0644 ${B}/flatbuffers/include/flatbuffers/*.h ${D}${includedir}/flatbuffers/
}

ALLOW_EMPTY:${PN} = "1"
