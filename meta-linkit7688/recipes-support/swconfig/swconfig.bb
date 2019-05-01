SUMMARY = "Tool for some on-board ethernet switches"
HOMEPAGE = "http://www.openwrt.org"
SECTION = "console/network"
LICENSE = "GPLv2 & LGPLv2.1"
DEPENDS = "libnl "

#SRC_URI = "svn://svn.openwrt.org/openwrt/trunk/package/network/config/swconfig;module=src;rev=45570;protocol=svn 
SRC_URI = " \
           file://swconfig.tar.gz \
           file://no-uci.patch;striplevel=2 \
           file://use-stdbool.patch \
           file://libnl-link-fix.patch \
           file://fix-swlib-includes.patch \
           file://swconfig.service \
           file://swconfig-init \
           file://LICENSE \
"

S = "${WORKDIR}/src"

LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=94d55d512a9ba36caa9b7df079bae19f \
                    file://swlib.c;beginline=6;endline=13;md5=1c4718c95e3c271867207e80c073fff9 \
                    file://cli.c;beginline=7;endline=14;md5=a2c41decfb4813a128acfceb5553953e \
"

inherit update-rc.d
INITSCRIPT_NAME = "swconfig-init"
INITSCRIPT_PARAMS = "start 06 S ."

CFLAGS_append = " -I. -I${STAGING_INCDIR}/libnl3 -std=gnu99"

do_configure() {
	mkdir -p "${S}/linux"
	cp "${STAGING_KERNEL_DIR}/include/uapi/linux/switch.h" "${S}/linux"
	true
}

do_compile() {
	oe_runmake
}

do_install() {
	install -d -m 0755 ${D}${sbindir}
	install -m 0755 swconfig ${D}/${sbindir}
        install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/swconfig-init ${D}${sysconfdir}/init.d
}

FILES_${PN} += "${sbindir}/* install ${sysconfdir}/init.d/*"
