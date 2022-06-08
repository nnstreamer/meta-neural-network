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
"

DEPENDS = "\
    iniparser \
    openblas \
    gtest \
    ml-api \
"

PV = "0.2.0+git${SRCPV}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit meson pkgconfig

EXTRA_OEMESON += "\
    -Dplatform=yocto \
    -Denable-blas=true \
    -Denable-cublas=false \
    -Denable-app=false \
    -Dinstall-app=false \
    -Duse_gym=false \
    -Denable-capi=true \
    -Denable-test=false \
    -Denable-logging=true \
    -Denable-tizen-feature-check=true \
    -Denable-nnstreamer-tensor-filter=false \
    -Denable-nnstreamer-backbone=false \
    -Denable-tflite-backbone=false \
    -Denable-tflite-interpreter=false \
    --bindir=${libdir}/${PN}/bin \
"

INSANE_SKIP:${PN} += "staticdev"

PACKAGES += "\
    ${PN}-unittest \
"

FILES:${PN} += "\
    ${libdir}/*.so \
    ${sysconfdir}/nntrainer.ini \
"

FILES:${PN}-unittest += "\
    ${bindir}/applications/unittest* \
"

FILES:${PN}-dev = "\
    ${includedir}/nntrainer/* \
    ${libdir}/*.a \
    ${bindir}/applications/libapp_utils.a \
    ${libdir}/pkgconfig/*.pc \
"

RDEPENDS:${PN}-unittest = "nntrainer"
