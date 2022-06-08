DESCRIPTION = "TensorFlow’s lightweight solution for mobile and embedded devices"
AUTHOR = "Google Inc. and Yuan Tang"
HOMEPAGE = "https://www.tensorflow.org/lite"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=01e86893010a1b87e69a213faa753ebd"

MD5SUM_EIGEN = "4c884968ede816a84c70e2cd2c81de8d"
SHA256SUM_EIGEN = "d956415d784fa4e42b6a2a45c32556d6aec9d0a3d8ef48baee2522ab762556a9"
MD5SUM_FARMHASH = "f039a65a7f62bdb6c4b4c8a732638d80"
SHA256SUM_FARMHASH = "6560547c63e4af82b0f202cb710ceabb3f21347a4b996db565a411da5b17aba0"
MD5SUM_GEMMLOWP = "a90a2161ad8ee4ccf724f56e07637601"
SHA256SUM_GEMMLOWP = "b87faa7294dfcc5d678f22a59d2c01ca94ea1e2a3b488c38a95a67889ed0a658"
MD5SUM_GTEST = "16877098823401d1bf2ed7891d7dce36"
SHA256SUM_GTEST = "58a6f4277ca2bc8565222b3bbd58a177609e9c488e8a72649359ba51450db7d8"
MD5SUM_ABSLCPP = "7c9945f256d3733a8eade255afcbd072"
SHA256SUM_ABSLCPP = "7dd09690ae7ca4551de3111d4a86b75b23ec17445f273d3c42bdcdc1c7b02e4e"
MD5SUM_NEON2SSE = "e08e9cf66876ca0acb5c9896f4c967b9"
SHA256SUM_NEON2SSE = "ea951915d21a2468f74be284fddc897facfa0279fed7cceb7b218d61edc75079"
MD5SUM_FLATBUFFER = "3811552512049fac3af419130904bc55"
SHA256SUM_FLATBUFFER = "b2bb0311ca40b12ebe36671bdda350b10c7728caf0cfe2d432ea3b6e409016f3"
MD5SUM_FFT = "4255dd8a74949d123216b1ab91520469"
SHA256SUM_FFT = "52bb637c70b971958ec79c9c8752b1df5ff0218a4db4510e60826e0cb79b5296"

SRC_URI = "git://github.com/tensorflow/tensorflow.git;branch=r1.12;protocol=https \
    https://mirror.bazel.build/bitbucket.org/eigen/eigen/get/fd6845384b86.tar.gz;md5sum=${MD5SUM_EIGEN};sha256sum=${SHA256SUM_EIGEN} \
    https://mirror.bazel.build/github.com/google/gemmlowp/archive/38ebac7b059e84692f53e5938f97a9943c120d98.zip;md5sum=${MD5SUM_GEMMLOWP};sha256sum=${SHA256SUM_GEMMLOWP} \
    https://github.com/google/googletest/archive/release-1.8.0.tar.gz;md5sum=${MD5SUM_GTEST};sha256sum=${SHA256SUM_GTEST} \
    https://github.com/abseil/abseil-cpp/archive/48cd2c3f351ff188bc85684b84a91b6e6d17d896.tar.gz;md5sum=${MD5SUM_ABSLCPP};sha256sum=${SHA256SUM_ABSLCPP} \
    https://github.com/intel/ARM_NEON_2_x86_SSE/archive/master.zip;md5sum=${MD5SUM_NEON2SSE};sha256sum=${SHA256SUM_NEON2SSE} \
    https://mirror.bazel.build/github.com/google/farmhash/archive/816a4ae622e964763ca0862d9dbd19324a1eaf45.tar.gz;md5sum=${MD5SUM_FARMHASH};sha256sum=${SHA256SUM_FARMHASH} \
    https://github.com/google/flatbuffers/archive/1f5eae5d6a135ff6811724f6c57f911d1f46bb15.tar.gz;md5sum=${MD5SUM_FLATBUFFER};sha256sum=${SHA256SUM_FLATBUFFER} \
    https://mirror.bazel.build/www.kurims.kyoto-u.ac.jp/~ooura/fft.tgz;md5sum=${MD5SUM_FFT};sha256sum=${SHA256SUM_FFT} \
    file://apply-modification-for-tflite-1.12-to-eigen.patch \
    file://tensorflow-lite.pc.in"

SRCREV = "${AUTOREV}"

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
LIBS = "-lstdc++ -lpthread -lm -lz -ldl"
BUILD_DEPS_DOWNLOAD_DIR_PREFIX = "${S}/tensorflow/contrib/lite/tools/make/downloads/"

do_cp_downloaded_build_deps() {
    mkdir -p ${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}eigen
    mkdir -p ${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}gemmlowp
    mkdir -p ${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}googletest
    mkdir -p ${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}absl
    mkdir -p ${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}farmhash
    mkdir -p ${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}neon_2_sse
    mkdir -p ${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}flatbuffers
    mkdir -p ${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}fft2d

    cp -rf ${WORKDIR}/eigen-eigen-fd6845384b86/* ${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}eigen
    cp -rf ${WORKDIR}/gemmlowp-38ebac7b059e84692f53e5938f97a9943c120d98/* ${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}gemmlowp
    cp -rf ${WORKDIR}/googletest-release-1.8.0/* ${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}googletest
    cp -rf ${WORKDIR}/abseil-cpp-48cd2c3f351ff188bc85684b84a91b6e6d17d896/* ${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}absl
    cp -rf ${WORKDIR}/farmhash-816a4ae622e964763ca0862d9dbd19324a1eaf45/* ${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}farmhash
    cp -rf ${WORKDIR}/ARM_NEON_2_x86_SSE-master/* ${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}neon_2_sse
    cp -rf ${WORKDIR}/flatbuffers-1f5eae5d6a135ff6811724f6c57f911d1f46bb15/* ${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}flatbuffers
    cp -rf ${WORKDIR}/fft/* ${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}fft2d
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
    oe_runmake -f tensorflow/contrib/lite/tools/make/Makefile clean
}

do_compile() {
    oe_runmake -f tensorflow/contrib/lite/tools/make/Makefile
}

do_install() {
    install -d ${D}${libdir}
    install -m 0644 ${S}/tensorflow/contrib/lite/tools/make/gen/${TARGET_OS}_${TUNE_ARCH}/lib/libtensorflow-lite.a ${D}${libdir}/
    install -d ${D}${includedir}/tensorflow/contrib/lite
    install -m 0644 ${S}/tensorflow/contrib/lite/*.h ${D}${includedir}/tensorflow/contrib/lite/
    install -d ${D}${includedir}/tensorflow/contrib/lite/c
    install -m 0644 ${S}/tensorflow/contrib/lite/c/*.h ${D}${includedir}/tensorflow/contrib/lite/c/
    install -d ${D}${includedir}/tensorflow/contrib/lite/core/api/
    install -m 0644 ${S}/tensorflow/contrib/lite/core/api/*.h ${D}${includedir}/tensorflow/contrib/lite/core/api/
    install -d ${D}${includedir}/tensorflow/contrib/lite/kernels
    install -m 0644 ${S}/tensorflow/contrib/lite/kernels/*.h ${D}${includedir}/tensorflow/contrib/lite/kernels/
    install -d  ${D}${includedir}/tensorflow/contrib/lite/profiling/
    install -m 0644 ${S}/tensorflow/contrib/lite/profiling/*.h ${D}${includedir}/tensorflow/contrib/lite/profiling/
    install -d ${D}${includedir}/tensorflow/contrib/lite/schema/
    install -m 0644 ${S}/tensorflow/contrib/lite/schema/*.h ${D}${includedir}/tensorflow/contrib/lite/schema/
    install -d ${D}${includedir}/tensorflow/contrib/lite/tools/
    install -m 0644 ${S}/tensorflow/contrib/lite/tools/*.h ${D}${includedir}/tensorflow/contrib/lite/tools/
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${WORKDIR}/tensorflow-lite.pc.in ${D}${libdir}/pkgconfig/tensorflow-lite.pc
    sed -i 's:@version@:${PV}:g
        s:@libdir@:${libdir}:g
        s:@includedir@:${includedir}:g' ${D}${libdir}/pkgconfig/tensorflow-lite.pc
    # flatbuffers
    install -d  ${D}${includedir}/flatbuffers
    install -m 0644 ${S}/tensorflow/contrib/lite/tools/make/downloads/flatbuffers/include/flatbuffers/*.h ${D}${includedir}/flatbuffers/
}

ALLOW_EMPTY:${PN} = "1"
