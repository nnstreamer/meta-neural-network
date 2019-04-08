SRC_URI = "git://github.com/google/flatbuffers.git"
SRCREV = "1f5eae5d6a135ff6811724f6c57f911d1f46bb15"

PACKAGES_remove = "${PN}-dbg ${PN}-src"

PROVIDES += "tflite-1.12-build-dep-${PN}"

# Override CMAKE options
EXTRA_OECMAKE = "\
    -DCMAKE_BUILD_TYPE=Release \
    -DFLATBUFFERS_BUILD_TESTS=OFF \
    -DFLATBUFFERS_BUILD_SHAREDLIB=OFF \
"
