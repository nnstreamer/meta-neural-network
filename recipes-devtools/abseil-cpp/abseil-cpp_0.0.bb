DESCRIPTION = "Abseil Common Libraries (C++)"
AUTHOR = "Google Inc."
HOMEPAGE = "https://abseil.io"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=df52c6edb7adc22e533b2bacc3bd3915"
PR = "git20190405.6cc6ac4"

SRC_URI = "git://github.com/abseil/abseil-cpp.git"
SRCREV = "6cc6ac44e065b9e8975fadfd6ccb99cbcf89aac4"

S = "${WORKDIR}/git"

inherit cmake
PROVIDES += "tflite-1.12-build-dep-absl-cpp"
