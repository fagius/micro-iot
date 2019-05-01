# Recipe created by recipetool
# #
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""
PR="r1"

SRC_URI = "file://pinmux.c \
           file://refclk.c \
           file://Makefile \
        "

S = "${WORKDIR}"

do_install () {
     install -d ${D}${sbindir}
     install -m 755 ${WORKDIR}/mt7688_pinmux ${D}${sbindir}
     install -m 755 ${WORKDIR}/mt7688_refclk ${D}${sbindir}
}

FILES_${PN} = "${sbindir}/*"
