SUMMARY = "NNtrainer, Software framework for training neural networks"
DESCRIPTION = "NNtrainer is Software Framework for Training Neural Network Models on Devices."

SECTION = "AI"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "\
    file://LICENSE;md5=095e13fef457e259d3bc155d0ed859f1 \
    file://debian/copyright;md5=06434e412e1de1e75fbd4f42920ebc4e \
"

SRC_URI = "\
    git://github.com/nnstreamer/nntrainer;branch=main;protocol=https \
    file://0001-Patch-for-yocto-build.patch \
"

DEPENDS = "\
    iniparser \
    openblas \
    gtest \
"

PV = "0.1.1+git${SRCPV}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit meson pkgconfig

EXTRA_OEMESON += "\
    -Denable-tizen=false \
    -Denable-blas=true \
    -Denable-cublas=false \
    -Denable-app=false \
    -Dinstall-app=false \
    -Duse_gym=false \
    -Denable-capi=true \
    -Denable-test=true \
    -Denable-logging=true \
    -Denable-tizen-feature-check=true \
    -Denable-nnstreamer-tensor-filter=false \
    --bindir=${libdir}/${PN}/bin \
"

do_install_append() {
    install -m 0644 ${WORKDIR}/build/*.dat ${D}${libdir}/${PN}/bin/applications/
    install -m 0644 ${WORKDIR}/build/*.in ${D}${libdir}/${PN}/bin/applications/
    install -m 0644 ${WORKDIR}/build/*.out ${D}${libdir}/${PN}/bin/applications/
}

INSANE_SKIP_${PN} += "staticdev"

PACKAGES += "\
    ${PN}-unittest \
"

FILES_${PN} += "\
    ${libdir}/*.so \
"

FILES_${PN}-unittest += "\
    ${bindir}/applications/unittest* \
"

FILES_${PN}-dev = "\
    ${includedir}/nntrainer/* \
    ${libdir}/*.a \
    ${bindir}/applications/libapp_utils.a \
    ${libdir}/pkgconfig/*.pc \
"

RDEPENDS_${PN}-unittest = "nntrainer"
