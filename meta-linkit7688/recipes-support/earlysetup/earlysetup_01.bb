SUMMARY = "iRobot board setup "
LICENSE="GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = " \
           file://earlysetup \
"

inherit autotools update-rc.d pkgconfig

INITSCRIPT_NAME = "earlysetup"
INITSCRIPT_PARAMS = "start 10 S ."

do_compile () {
}

do_install () {
        install -d ${D}/data
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${WORKDIR}/earlysetup ${D}${sysconfdir}/init.d/
}

FILES_${PN} += "${sysconfdir}/* /data/"

