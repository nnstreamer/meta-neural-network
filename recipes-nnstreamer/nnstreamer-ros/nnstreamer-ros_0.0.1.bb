SUMMARY = "NNStreamer extension plugins for ROS support"
DESCRIPTION = "A set of NNStreamer extension plugins for ROS support"
SECTION = "AI"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0244e07aff1ef055b85e686207429f23"

DEPENDS = "glib-2.0 gstreamer1.0 nnstreamer catkin cpp-common roscpp rosbag rospack"

SRC_URI = "git://github.com/nnsuite/nnstreamer-ros.git;protocol=https"

PV = "0.0.1+git${SRCPV}"
SRCREV = "${AUTOREV}"
S = "${WORKDIR}/git"
BBCLASSEXTEND = "native nativesdk"

ROS_VERSION = "${ROSDISTRO}"
ROS_BASE_PREFIX = "${WORKDIR}/recipe-sysroot/opt/ros"
ROS_ROOT = "${ROS_BASE_PREFIX}"
ROS_INSTALL_PREFIX = "/opt/ros"
ROS_INSTALL_BASE = "${ROS_INSTALL_PREFIX}/${ROS_VERSION}"

export CMAKE_PREFIX_PATH = "/opt/ros/${ROSDISTRO}"
export PYTHONPATH="${WORKDIR}/recipe-sysroot/opt/ros/${ROSDISTRO}/lib/python2.7/site-packages"

EXTRA_OECMAKE += "\
            -DROS_VERSION=${ROS_VERSION} \
            -DROS_BASE_PREFIX=${ROS_BASE_PREFIX} \
            -DROS_ROOT=${ROS_BASE_PREFIX} \
            -DROS_INSTALL_PREFIX=${ROS_INSTALL_PREFIX} \
            -DNNS_INSTALL_LIBDIR=${libdir} \
             "

inherit cmake
OECMAKE_FIND_ROOT_PATH_MODE_PROGRAM = "BOTH"

do_install_append() {
    find ${D} -name *.pyc -delete
    rm -f ${D}${ROS_INSTALL_BASE}/.rosinstall
    rm -f ${D}${ROS_INSTALL_BASE}/local_setup.bash
    rm -f ${D}${ROS_INSTALL_BASE}/setup.bash
    rm -f ${D}${ROS_INSTALL_BASE}/setup.zsh
    rm -f ${D}${ROS_INSTALL_BASE}/local_setup.sh
    rm -f ${D}${ROS_INSTALL_BASE}/local_setup.zsh
    rm -f ${D}${ROS_INSTALL_BASE}/setup.sh
    rm -f ${D}${ROS_INSTALL_BASE}/.catkin
    rm -f ${D}${ROS_INSTALL_BASE}/env.sh
    rm -f ${D}${ROS_INSTALL_BASE}/_setup_util.py
    # nodejs
    rm -rf ${D}${ROS_INSTALL_BASE}/share/gennodejs
    find ${D} -name *.js -delete
    # lisp
    rm -rf ${D}${ROS_INSTALL_BASE}/share/roseus
    rm -rf ${D}${ROS_INSTALL_BASE}/share/common-lisp
}

FILES_${PN} += "\
                ${ROS_INSTALL_BASE}/share/nns_ros_bridge/* \
                ${ROS_INSTALL_BASE}/lib/python2.7/dist-packages/* \
                ${ROS_INSTALL_BASE}/lib/*.so \
                ${libdir}/gstreamer-1.0/*.so \
                "

FILES_${PN}-dev += "\
                ${ROS_INSTALL_BASE}/share/nns_ros_bridge/cmake/* \
                ${ROS_INSTALL_BASE}/include/nns_ros_bridge/* \
                ${ROS_INSTALL_BASE}/include/nns_ros_bridge/cmake/* \
                ${ROS_INSTALL_BASE}/lib/pkgconfig/*.pc \
                "
