require recipes-graphics/xorg-xserver/xserver-xf86-dri-lite.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=3dd2bbe3563837f80ed8926b06c1c353"

PROTO_DEPS += "xf86driproto dri2proto"

DEPENDS += "font-util"

PE = "1"
PR = "r0"

SRC_URI += "file://nodolt.patch \
            file://crosscompile.patch"

# Misc build failure for master HEAD
SRC_URI += "file://fix_open_max_preprocessor_error.patch"

SRC_URI[md5sum] = "5c8773499a6a8c1ddaedf33577ec9634"
SRC_URI[sha256sum] = "8b30800004c98fc7a8e6ff31a339f28451be5132e774443be22bf226e1791e34"

RDEPENDS_${PN} += "xserver-xf86-emgd-bin mesa-dri"

COMPATIBLE_MACHINE = "crownbay"

EXTRA_OECONF += "--enable-dga --enable-dri --enable-dri2"
