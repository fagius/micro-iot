FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_append() {
        # bitbake package manager complained about this file, so remove it
        if [ -f ${D}${LIBDIR}/usr/lib/python3.7/config-3.7m/libpython3.7m.a ]; then
          rm ${D}${LIBDIR}/usr/lib/python3.7/config-3.7m/libpython3.7m.a
        fi
}

