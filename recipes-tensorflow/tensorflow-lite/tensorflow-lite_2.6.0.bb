DESCRIPTION = "TensorFlow’s lightweight solution for mobile and embedded devices"
AUTHOR = "Google Inc. and Yuan Tang"
HOMEPAGE = "https://www.tensorflow.org/lite"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c7e17cca1ef4230861fb7868e96c387e"

DEPENDS = "zlib"
SRC_URI = " \
    git://github.com/tensorflow/tensorflow;destsuffix=git/;branch=r2.6;rev=v2.6.0;protocol=https \
    file://fix-to-cmake.patch \
    file://tensorflow2-lite.pc.in \
"

inherit cmake

S = "${WORKDIR}/git"

# -DTFLITE_BUILD_SHARED_LIB=on

EXTRA_OECMAKE = "\
    -DTFLITE_ENABLE_XNNPACK=on \
    -DTFLITE_ENABLE_RUY=on \
    -DTFLITE_ENABLE_GPU=off \
    ${S}/tensorflow/lite/ \
"

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
