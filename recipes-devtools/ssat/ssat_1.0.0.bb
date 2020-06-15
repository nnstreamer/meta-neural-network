SUMMARY = "Shell Script Automated Tester (unit testing executable files)"
DESCRIPTION = "Shell Script Automated Tester (unit testing executable files)"

SECTION = "Development"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
                    file://debian/copyright;md5=9ad9849cc2d52d5b8200d053d510e7d2"

SRC_URI = "git://git.tizen.org/platform/upstream/SSAT"

PV = "1.0.0+git${SRCPV}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

do_install () {
     install -d ${D}${bindir}
     install -p -m 0755 ${S}/ssat.sh ${D}${bindir}/
     install -p -m 0644 ${S}/ssat-api.sh ${D}${bindir}/
     cd ${D}${bindir}
     ln -s ssat.sh ssat
}

sysroot_stage_all_append() {
 install -d ${SYSROOT_DESTDIR}${bindir}
 install -p -m 0755 ${D}${bindir}/ssat.sh ${SYSROOT_DESTDIR}${bindir}
 install -p -m 0755 ${D}${bindir}/ssat-api.sh ${SYSROOT_DESTDIR}${bindir}
 cd ${SYSROOT_DESTDIR}${bindir}
 ln -s ssat.sh ssat
}

FILES_${PN} += "${bindir}/ssat \
	    ${bindir}/ssat.sh \
	    ${bindir}/ssat-api.sh"
