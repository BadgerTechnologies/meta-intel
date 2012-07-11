FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI = "git://git.yoctoproject.org/linux-yocto-3.0;protocol=git;bareclone=1;branch=${KBRANCH},meta,yocto/pvr;name=machine,meta,pvr"

SRC_URI_cedartrail-nopvr = "git://git.yoctoproject.org/linux-yocto-3.0;protocol=git;nocheckout=1;branch=${KBRANCH},meta;name=machine,meta"

COMPATIBLE_MACHINE_cedartrail = "cedartrail"
KMACHINE_cedartrail  = "cedartrail"
KBRANCH_cedartrail  = "yocto/standard/cedartrail"
KERNEL_FEATURES_append_cedartrail += "bsp/cedartrail/cedartrail-pvr-merge.scc"
KERNEL_FEATURES_append_cedartrail += "cfg/efi-ext.scc"

COMPATIBLE_MACHINE_cedartrail-nopvr = "cedartrail"
KMACHINE_cedartrail-nopvr  = "cedartrail"
KBRANCH_cedartrail-nopvr  = "yocto/standard/cedartrail"
KERNEL_FEATURES_append_cedartrail-nopvr += " cfg/smp.scc"

SRCREV_machine_pn-linux-yocto_cedartrail ?= "1e79e03d115ed177882ab53909a4f3555e434833"
SRCREV_meta_pn-linux-yocto_cedartrail ?= "46e8fc2bbbe73514e8d99101adaaa373f760ffa7"
SRCREV_pvr_pn-linux-yocto_cedartrail ?= "997f5644664b31ebefd6e16c451c4a3729eb378a"

SRCREV_machine_pn-linux-yocto_cedartrail-nopvr ?= "1e79e03d115ed177882ab53909a4f3555e434833"
SRCREV_meta_pn-linux-yocto_cedartrail-nopvr ?= "a4ac64fe873f08ef718e2849b88914725dc99c1c"
