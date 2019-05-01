# linux-yocto-linkit7688 kernel
#

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

KBRANCH="v4.14.95"

# Original openwrt commit (forked from): c5ca1c9ab65bfe1e6fc74230f8c0121230562b1c

# Override SRC_URI in a copy of this recipe to point at a different source
# tree if you do not want to build from Linus' tree.
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;protocol=git;nobranch=1;rev=3b68e5cf57f08ad1a9dd7f8ca48ae1326ac98824"

SRC_URI += "file://v4.14.95/defconfig \
            file://linkit7688.scc \
            file://linkit7688.cfg \
            file://linkit7688-standard.scc \
            file://linkit7688-user-config.cfg \
            file://linkit7688-user-features.scc \
            file://debug.cfg \
            file://v4.14.95/linkit7688-user-patches.scc \
            file://v4.14.95/0001-linkit7688-dts-makefile.patch \
            file://v4.14.95/lks7688_ubinize.cfg \
           "

SRC_URI += "file://openwrt_files/target/linux/generic/files"
SRC_URI += "file://openwrt_files/target/linux/ramips/files-4.14"
SRC_URI += "file://openwrt_files/target/linux/ramips/dts-4.14"

LINUX_VERSION ?= "4.14"
LINUX_VERSION_EXTENSION_append = "-custom"

# Modify SRCREV to a different commit hash in a copy of this recipe to
# build a different release of the Linux kernel.
# tag: v4.2 64291f7db5bd8150a74ad2036f1037e6a0428df2
# SRCREV_machine="b3e88217e2f95b004da89a0ff931e1dc45d3d094"
# SRCREV="b3e88217e2f95b004da89a0ff931e1dc45d3d094"

PV = "${LINUX_VERSION}+git${SRCPV}"

# Override COMPATIBLE_MACHINE to include your machine in a copy of this recipe
# file. Leaving it empty here ensures an early explicit build failure.
COMPATIBLE_MACHINE_linkit7688 = "linkit7688"

PREFERRED_PROVIDER_virtual/kernel="linux-yocto-linkit7688"

KERNEL_VERSION_SANITY_SKIP="1"


# KERNEL_DEFCONFIG_linkit7688="defconfig"

FILESEXTRAPATHS_prepend:="${THISDIR}/openwrt_files:"

DEPENDS+="image-patch-native xz-native u-boot-mkimage-native openwrt-lzma-native"

do_patch_prepend() {
    cp -r openwrt_files/target/linux/generic/files/* ${S}
    cp -r openwrt_files/target/linux/ramips/files-4.14/* ${S}
    cp -r openwrt_files/target/linux/ramips/dts-4.14/* ${S}/arch/mips/boot/dts/ralink
}

do_configure_prepend() {
    rm -rf {B}/.config
}

#KERNEL_DEVICETREE = "${S}/arch/mips/boot/dts/ralink/LINKIT7688.dts"
#KERNEL_DEVICETREE += "LINKIT7688.dtb"

do_compile_append () {
    cd ${B}
    make dtbs
}

do_install_prepend() {
    cd ${B}
    ${OBJCOPY} -O binary -R .reginfo -R .notes -R .note -R .comment -R .mdebug -R .note.gnu.build-id -S vmlinux vmlinux.bin
    cp arch/mips/boot/dts/ralink/LINKIT7688.dtb ${B}
    patch-dtb vmlinux.bin LINKIT7688.dtb
    openwrt-lzma e vmlinux.bin -lc1 -lp2 -pb2 vmlinux.bin.lzma
    # cp vmlinux vmlinux_
    # lzma -z --lzma1=lc=1,lp=2,pb=2 vmlinux
    # lzma -z vmlinux
    # mv vmlinux.lzma vmlinux.bin.lzma
    # mv vmlinux_ vmlinux

    mkimage -A mips -O linux -T kernel -C lzma -a 0x80000000 -e 0x80000000 -n LINUX -d vmlinux.bin.lzma uImage
}

kernel_do_deploy_append() {
    install -m 0644 ${B}/uImage ${DEPLOYDIR}/uImage
    install -m 0644 ${WORKDIR}/v4.14.95/lks7688_ubinize.cfg ${DEPLOYDIR}/lks7688_ubinize.cfg
}

# Disabling stripping.
# Allows kernel modules recompile. Vmlinux cannot be stripped for that.
do_strip() {
}
