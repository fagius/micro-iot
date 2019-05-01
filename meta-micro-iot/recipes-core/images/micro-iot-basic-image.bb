require recipes-core/images/core-image-minimal.bb

DEPENDS = "linux-yocto-linkit7688 padjffs2-native"

IMAGE_FSTYPES = "squashfs-xz jffs2"
# IMAGE_FEATURES += "read-only-rootfs"

# EXTRA_IMAGECMD_jffs2_remove = "--pad"
EXTRA_IMAGECMD_jffs2 += "-x lzo -x zlib"

IMAGE_INSTALL += "mtd-utils"
IMAGE_INSTALL += "mtd-utils-ubifs"
IMAGE_INSTALL += "iw"
IMAGE_INSTALL += "alsa-utils-amixer"
IMAGE_INSTALL += "alsa-utils-aplay"
IMAGE_INSTALL += "alsa-utils-speakertest"
IMAGE_INSTALL += "swconfig"
IMAGE_INSTALL += "mt7688-utils"
IMAGE_INSTALL += "earlysetup"
IMAGE_INSTALL += "wpa-supplicant"
IMAGE_INSTALL += "packagegroup-core-ssh-dropbear"

do_prepare_sysupgrade() {
    cd ${DEPLOY_DIR_IMAGE}
    ubinize -o base-image-ubi.img -m 1 -p 0x10000 -O 4096 lks7688_ubinize.cfg
    dd if=${DEPLOY_DIR_IMAGE}/uImage >  ${DEPLOY_DIR_IMAGE}/lks7688.img
    dd if=${DEPLOY_DIR_IMAGE}/base-image-ubi.img >>  ${DEPLOY_DIR_IMAGE}/lks7688.img
}

# Call function prepare_sysupgrade to generate images
addtask prepare_sysupgrade after do_image_complete before do_populate_lic_deploy
