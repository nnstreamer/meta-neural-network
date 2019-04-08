DESCRIPTION = "A header file redefining ARM Neon intrinsics in terms of SSE intrinsics \
    allowing neon code to compile and run on x64/x86 workstantions."
AUTHOR = "Intel Corporation, Victoria Zhislina"
HOMEPAGE = "https://github.com/intel/ARM_NEON_2_x86_SSE"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b4fdfcb48f273d192333c498d75fa26f"
PR = "git2019026.91c5962"

SRC_URI = "git://github.com/intel/ARM_NEON_2_x86_SSE.git"
SRCREV = "91c5962678271b9c224b5d52a149b19065e65135"

S = "${WORKDIR}/git"

inherit cmake
PROVIDES += "tflite-1.12-build-dep-${PN}"

