FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
        file://fragment.cfg;subdir=busybox-1.30.1 \
        file://udhcpd.conf \
        file://mdev.conf \
        "

do_install_append () {
        install -d ${D}${sysconfdir}
        install -m 0644 ${WORKDIR}/udhcpd.conf ${D}${sysconfdir}
        install -m 0644 ${WORKDIR}/mdev.conf ${D}${sysconfdir}
}

