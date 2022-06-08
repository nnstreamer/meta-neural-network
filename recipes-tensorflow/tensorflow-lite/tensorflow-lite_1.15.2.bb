DESCRIPTION = "TensorFlow’s lightweight solution for mobile and embedded devices"
AUTHOR = "Google Inc. and Yuan Tang"
HOMEPAGE = "https://www.tensorflow.org/lite"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=64a34301f8e355f57ec992c2af3e5157"

MD5SUM_FFT = "4255dd8a74949d123216b1ab91520469"
SHA256SUM_FFT = "52bb637c70b971958ec79c9c8752b1df5ff0218a4db4510e60826e0cb79b5296"

SRC_URI = " \
	git://github.com/tensorflow/tensorflow;destsuffix=git/;branch=r1.15;rev=v1.15.2;protocol=https \
	git://gitlab.com/libeigen/eigen.git;destsuffix=eigen/;branch=master;rev=cdb377d0cba4889fc909d1bbdd430b988db0db97;protocol=https \
	git://github.com/google/gemmlowp;destsuffix=gemmlowp/;branch=master;rev=2483d846ad865dd4190fe4a1a1ba2d9cfcea78e1;protocol=https \
	git://github.com/google/googletest;destsuffix=googletest/;branch=master;rev=release-1.10.0;protocol=https \
	git://github.com/abseil/abseil-cpp;destsuffix=abseil-cpp/;branch=master;rev=20190808;nobranch=1;protocol=https \
	git://github.com/intel/ARM_NEON_2_x86_SSE;destsuffix=neon_2_sse/;branch=master;rev=8dbe2461c89760ac4b204aa0eafb72413a97957d;protocol=https \
	git://github.com/google/farmhash;destsuffix=farmhash/;branch=master;rev=0d859a811870d10f53a594927d0d0b97573ad06d;protocol=https \
	git://github.com/google/flatbuffers;destsuffix=flatbuffers/;branch=master;rev=v1.11.0;protocol=https \
	https://mirror.bazel.build/www.kurims.kyoto-u.ac.jp/~ooura/fft.tgz;md5sum=${MD5SUM_FFT};sha256sum=${SHA256SUM_FFT} \
	file://apply-modification-for-tflite-1.15-to-eigen.patch \
	file://tensorflow-lite.pc.in \
"

#SRCREV = "v1.15.2"

S = "${WORKDIR}/git"

DEPENDS = "zlib"
TARGET_CFLAGS:remove = "-O2"
TARGET_CPPLAGS:remove = "-O2"
TARGET_CXXLAGS:remove = "-O2"
CCFLAGS:append = " -O3 -DNDEBUG -fPIC -DGEMMLOWP_ALLOW_SLOW_SCALAR_FALLBACK \
    -I${STAGING_INCDIR}"
CXXFLAGS:append = " -O3 -DNDEBUG -fPIC -DGEMMLOWP_ALLOW_SLOW_SCALAR_FALLBACK \
    -I${STAGING_INCDIR}"
LDFLAGS:remove = "-Wl,-O1"
CXXFLAGS:append = " -O3 -DNDEBUG -fPIC -DGEMMLOWP_ALLOW_SLOW_SCALAR_FALLBACK \
    -I${STAGING_INCDIR}"
LIBS = "-lstdc++ -lpthread -lm -lz -ldl -lrt"
BUILD_DEPS_DOWNLOAD_DIR_PREFIX = "${S}/tensorflow/lite/tools/make/downloads/"

do_cp_downloaded_build_deps() {
    mkdir -p "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}eigen"
    mkdir -p "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}gemmlowp"
    mkdir -p "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}googletest"
    mkdir -p "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}absl"
    mkdir -p "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}farmhash"
    mkdir -p "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}neon_2_sse"
    mkdir -p "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}flatbuffers"
    mkdir -p "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}fft2d"

    cp -rf ${WORKDIR}/eigen/*       "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}eigen/"
    cp -rf ${WORKDIR}/gemmlowp/*    "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}gemmlowp/"
    cp -rf ${WORKDIR}/googletest/*  "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}googletest/"
    cp -rf ${WORKDIR}/abseil-cpp/*  "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}absl/"
    cp -rf ${WORKDIR}/farmhash/*    "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}farmhash/"
    cp -rf ${WORKDIR}/neon_2_sse/*  "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}neon_2_sse/"
    cp -rf ${WORKDIR}/flatbuffers/* "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}flatbuffers/"
    cp -rf ${WORKDIR}/fft/*         "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}fft2d/"
}
addtask do_cp_downloaded_build_deps after do_unpack before do_patch

EXTRA_OEMAKE = "\
    'CC=${CC}' \
    'CXX=${CXX}' \
    'CPP=${CPP}' \
    'CFLAGS=${CFLAGS}' \
    'CPPFLAGS=${CFLAGS}' \
    'CXXFLAGS=${CXXFLAGS}' \
    'AR=${AR}' \
    'LD=${LD}' \
    'LDFLAGS=${LDFLAGS}' \
    'LIBS=${LIBS}' \
    'TARGET=${TARGET_OS}' \
    'TARGET_ARCH=${TUNE_ARCH}'"

do_configure() {
    oe_runmake -f tensorflow/lite/tools/make/Makefile clean
}

do_compile() {
    oe_runmake -f tensorflow/lite/tools/make/Makefile
}

do_install() {
    install -d ${D}${libdir}
    install -m 0644 ${S}/tensorflow/lite/tools/make/gen/${TARGET_OS}_${TUNE_ARCH}/lib/libtensorflow-lite.a ${D}${libdir}/
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${WORKDIR}/tensorflow-lite.pc.in ${D}${libdir}/pkgconfig/tensorflow-lite.pc

    cd "${S}/tensorflow/lite"
    for file in $(find . -name '*.h'); do
        install -d "${D}${includedir}/tensorflow/lite/$(dirname -- "${file}")"
        install -m 0644 "${file}" "${D}${includedir}/tensorflow/lite/${file}"
    done

    # For backward compatibility with v1.09, where tf-lite was in contrib
    mkdir -p ${D}${includedir}/tensorflow/contrib
    cd ${D}${includedir}/tensorflow/contrib && ln -sf ../lite

    sed -i 's:@version@:${PV}:g
        s:@libdir@:${libdir}:g
        s:@includedir@:${includedir}:g' ${D}${libdir}/pkgconfig/tensorflow-lite.pc
    # flatbuffers
    install -d  ${D}${includedir}/flatbuffers
    install -m 0644 ${S}/tensorflow/lite/tools/make/downloads/flatbuffers/include/flatbuffers/*.h ${D}${includedir}/flatbuffers/
}

ALLOW_EMPTY:${PN} = "1"
