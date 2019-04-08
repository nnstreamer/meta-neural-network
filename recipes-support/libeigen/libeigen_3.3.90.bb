DESCRIPTION = "Eigen is a C++ template library for linear algebra: matrices, vectors, numerical solvers, and related algorithms."
AUTHOR = "Benoît Jacob, Gaël Guennebaud and others"
HOMEPAGE = "http://eigen.tuxfamily.org/"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://COPYING.MPL2;md5=815ca599c9df247a0c7f619bab123dad"
PV = "3.3.90"

SRC_URI = "hg://bitbucket.org/eigen/eigen;protocol=https;module=3.3"

SRCREV = "10938:fd6845384b86"
S = "${WORKDIR}/3.3"
DEVEL_PKG_NAME = "${PN}3-dev"

PACKAGES = "${DEVEL_PKG_NAME}"
PROVIDES += "tflite-1.12-build-dep-${PN}3"

inherit cmake
FILES_${DEVEL_PKG_NAME} = "${includedir} ${datadir}/eigen3/cmake ${datadir}/pkgconfig"
