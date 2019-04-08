DESCRIPTION = "Google Testing and Mocking Framework"
HOMEPAGE = "https://github.com/google/googletest"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://googlemock/LICENSE;md5=cbbd27594afd089daa160d3a16dd515a \
                    file://googletest/LICENSE;md5=cbbd27594afd089daa160d3a16dd515a"

PROVIDES += "tflite-1.12-build-dep-gtest tflite-1.12.build-dep-gmock"

S = "${WORKDIR}/googletest-release-1.8.0"
SRC_URI = "https://github.com/google/googletest/archive/release-1.8.0.tar.gz"
SRC_URI[md5sum] = "16877098823401d1bf2ed7891d7dce36"
SRC_URI[sha256sum] = "58a6f4277ca2bc8565222b3bbd58a177609e9c488e8a72649359ba51450db7d8"

PACKAGES = "${PN}-dev ${PN}-staticdev"

inherit cmake

RDEPENDS_${PN}-dev = "${PN}-staticdev"

BBCLASSEXTEND = "native nativesdk"
