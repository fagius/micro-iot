<p align ="center"><img src=logo-big.svg /></p>  

# Micro-iot
Yocto based Linux distribution for Internet of Things capable low profile SBCs

### Supported modules:
1. SeeedStudio LinkitSmart MT7688 (Mediatek MT7688AN chipset with MIPS24KEc core)

### Project goals and principles:
- transparency
- simplicity (KISS & YAGNI)
- quality

### Features
- Linux kernel 4.14.95 with OpenWRT patches
- UBI squashfs rootfs
- flashing via MT7688 u-boot
- OpenWRT backported Wifi drivers and MT76 driver compiled out-of-tree
- sound card enabled for use with the wm8960 codec chip on the LinkitSmart 7688 breakout board  
- auto mount micro SD cards.  if available as the first partition on the SD card, mount the EXT4 filesystem and use it as persistent storage
- tested with Yocto Release Thud (rev. 9818acbb00b6f78dbfcc5796ec78bf25f6535288)

### Quick Start  
git clone git://git.yoctoproject.org/poky  
cd poky  
git checkout 9818acbb00b6f78dbfcc5796ec78bf25f6535288  
cd poky  
git clone https://github.com/fagius/micro-iot.git  
cd micro-iot  
mv meta-linkit7688 ..  
mv build ..  
mv meta-micro-iot ..  
cd ..  
. oe-init-build-env build  
bitbake micro-iot-basic-image  

build artifact poky/build/tmp/deploy/images/linkit7688/lks7688.img can be flashed to the linkitsmart 7688 board  

### Firmware Install Start  
put lks7688.img on the root directory of a USB flash drive.
connect the flash drive to the host micro usb connector of the linkit smart board.  
press and hold the board's wifi button.  continue holding wifi button while pressing and releasing the pwr button.  continue holding the wifi button while the yellow wifi led is
on.  release the wifi button when the yellow wifi led goes off.  factory uboot will install the firmware from the lks7688.img file.  


### Audio generation test  
the linkitsmart 7688 board must be connected to its breakout board (the board with the wm8960 codec chip)  
boot the board  
connect a headphone to the headphone jack.  this command should generate a tone:  

speaker-test -c0 -s1 -f250 -t sine  


