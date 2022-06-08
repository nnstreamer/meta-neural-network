DESCRIPTION = "TensorFlow’s lightweight solution for mobile and embedded devices"
AUTHOR = "Google Inc. and Yuan Tang"
HOMEPAGE = "https://www.tensorflow.org/lite"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=64a34301f8e355f57ec992c2af3e5157"

SHA256SUM_EIGEN = "f632d82e43ffc46adfac9043beace700b0265748075e7edc0701d81380258038"
SHA256SUM_ABSLCPP = "f368a8476f4e2e0eccf8a7318b98dafbe30b2600f4e3cf52636e5eb145aba06a"
SHA256SUM_GEMMLOWP = "43146e6f56cb5218a8caaab6b5d1601a083f1f31c06ff474a4378a7d35be9cfb"
SHA256SUM_RUY = "8fd4adeeff4f29796bf7cdda64806ec0495a2435361569f02afe3fe33406f07c"
SHA256SUM_NEON2SSE = "213733991310b904b11b053ac224fee2d4e0179e46b52fe7f8735b8831e04dcc"
SHA256SUM_FARMHASH = "6560547c63e4af82b0f202cb710ceabb3f21347a4b996db565a411da5b17aba0"
SHA256SUM_FLATBUFFER = "62f2223fb9181d1d6338451375628975775f7522185266cd5296571ac152bc45"
SHA256SUM_FP16 = "9635d6245762714237d165e11f4359599eeb140ccc800bfe247948f167daabf6"
SHA256SUM_FFT2D = "ada7e99087c4ed477bfdf11413f2ba8db8a840ba9bbf8ac94f4f3972e2a7cec9"
SHA256SUM_GTEST = "58a6f4277ca2bc8565222b3bbd58a177609e9c488e8a72649359ba51450db7d8"

SRC_URI = " \
    git://github.com/tensorflow/tensorflow;destsuffix=git/;branch=r2.3;rev=v2.3.0;protocol=https \
    https://gitlab.com/libeigen/eigen/-/archive/386d809bde475c65b7940f290efe80e6a05878c4/eigen-386d809bde475c65b7940f290efe80e6a05878c4.tar.gz;sha256sum=${SHA256SUM_EIGEN} \
    https://github.com/abseil/abseil-cpp/archive/df3ea785d8c30a9503321a3d35ee7d35808f190d.tar.gz;sha256sum=${SHA256SUM_ABSLCPP} \
    https://github.com/google/gemmlowp/archive/fda83bdc38b118cc6b56753bd540caa49e570745.zip;sha256sum=${SHA256SUM_GEMMLOWP} \
    https://github.com/google/ruy/archive/34ea9f4993955fa1ff4eb58e504421806b7f2e8f.zip;sha256sum=${SHA256SUM_RUY} \
    https://github.com/intel/ARM_NEON_2_x86_SSE/archive/1200fe90bb174a6224a525ee60148671a786a71f.tar.gz;sha256sum=${SHA256SUM_NEON2SSE} \
    https://github.com/google/farmhash/archive/816a4ae622e964763ca0862d9dbd19324a1eaf45.tar.gz;sha256sum=${SHA256SUM_FARMHASH} \
    https://github.com/google/flatbuffers/archive/v1.12.0.tar.gz;sha256sum=${SHA256SUM_FLATBUFFER} \
    https://github.com/Maratyszcza/FP16/archive/febbb1c163726b5db24bed55cc9dc42529068997.zip;sha256sum=${SHA256SUM_FP16} \
    https://storage.googleapis.com/mirror.tensorflow.org/www.kurims.kyoto-u.ac.jp/~ooura/fft2d.tgz;sha256sum=${SHA256SUM_FFT2D} \
    https://github.com/google/googletest/archive/release-1.8.0.tar.gz;sha256sum=${SHA256SUM_GTEST} \
    file://tensorflow-lite.pc.in \
"

S = "${WORKDIR}/git"

DEPENDS = "zlib"
TARGET_CFLAGS:remove = "-O2"
TARGET_CPPLAGS:remove = "-O2"
TARGET_CXXLAGS:remove = "-O2"
CCFLAGS:append = " -O3 -DNDEBUG -fPIC -DGEMMLOWP_ALLOW_SLOW_SCALAR_FALLBACK -DTFLITE_WITH_RUY -DTFLITE_WITHOUT_XNNPACK -DBUILD_WITH_NNAPI=false \
    -I${STAGING_INCDIR}"
CXXFLAGS:append = " -O3 -DNDEBUG -fPIC -DGEMMLOWP_ALLOW_SLOW_SCALAR_FALLBACK -DTFLITE_WITH_RUY -DTFLITE_WITHOUT_XNNPACK -DBUILD_WITH_NNAPI=false \
    -I${STAGING_INCDIR}"
LDFLAGS:remove = "-Wl,-O1"
LIBS = "-lstdc++ -lpthread -lm -lz -ldl -lrt"
BUILD_DEPS_DOWNLOAD_DIR_PREFIX = "${S}/tensorflow/lite/tools/make/downloads/"

do_cp_downloaded_build_deps() {
    mkdir -p "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}eigen"
    mkdir -p "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}absl"
    mkdir -p "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}gemmlowp"
    mkdir -p "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}ruy"
    mkdir -p "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}neon_2_sse"
    mkdir -p "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}farmhash"
    mkdir -p "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}flatbuffers"
    mkdir -p "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}fp16"
    mkdir -p "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}fft2d"
    mkdir -p "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}googletest"

    cp -rf ${WORKDIR}/eigen-386d809bde475c65b7940f290efe80e6a05878c4/*              "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}eigen/"
    cp -rf ${WORKDIR}/abseil-cpp-df3ea785d8c30a9503321a3d35ee7d35808f190d/*         "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}absl/"
    cp -rf ${WORKDIR}/gemmlowp-fda83bdc38b118cc6b56753bd540caa49e570745/*           "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}gemmlowp/"
    cp -rf ${WORKDIR}/ruy-34ea9f4993955fa1ff4eb58e504421806b7f2e8f/*                "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}ruy/"
    cp -rf ${WORKDIR}/ARM_NEON_2_x86_SSE-1200fe90bb174a6224a525ee60148671a786a71f/* "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}neon_2_sse/"
    cp -rf ${WORKDIR}/farmhash-816a4ae622e964763ca0862d9dbd19324a1eaf45/*           "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}farmhash/"
    cp -rf ${WORKDIR}/flatbuffers-1.12.0/*                                          "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}flatbuffers/"
    cp -rf ${WORKDIR}/FP16-febbb1c163726b5db24bed55cc9dc42529068997/*               "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}fp16/"
    cp -rf ${WORKDIR}/fft2d/*                                                       "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}fft2d/"
    cp -rf ${WORKDIR}/googletest-release-1.8.0/*                                    "${BUILD_DEPS_DOWNLOAD_DIR_PREFIX}googletest/"
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
