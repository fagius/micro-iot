FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
        file://rcS \
"

do_configure_append () {
        # comment out kill, was causing immediate shutdowns at boot time
        sed -i -e 's,^kill -USR1 1,# kill -USR1 1,g' ${S}/mountall.sh
}


do_install_append() {
        install -d ${D}${sysconfdir}/init.d/
        install -d ${D}${sysconfdir}/default/
        install -m 0755 ${WORKDIR}/rcS ${D}${sysconfdir}/default/rcS
}

FILES_${PN} += "${sysconfir}/init.d/* "
