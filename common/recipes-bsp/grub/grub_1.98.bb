SUMMARY = "GRUB2 is the next-generation GRand Unified Bootloader"

DESCRIPTION = "GRUB2 is the next generaion of a GPLed bootloader \
intended to unify bootloading across x86 operating systems. In \
addition to loading the Linux kernel, it implements the Multiboot \
standard, which allows for flexible loading of multiple boot images."

HOMEPAGE = "http://www.gnu.org/software/grub/"
SECTION = "bootloaders"
PRIORITY = "optional"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

RDEPENDS = "diffutils"
PR = "r0"

SRC_URI = "ftp://alpha.gnu.org/gnu/grub/grub-${PV}.tar.gz \
          file://uninit-shdr-fix.patch;apply=yes \
          file://grub-install.in.patch;apply=yes \
          file://40_custom"

inherit autotools
inherit gettext

EXTRA_OECONF = "--with-platform=pc --target=i386"

do_configure() {
    oe_runconf
}

python __anonymous () {
    import re
    host = bb.data.getVar('HOST_SYS', d, 1)
    if not re.match('x86.64.*-linux', host):
        raise bb.parse.SkipPackage("incompatible with host %s" % host)
}

do_install_append () {
    install -m 0755 ${WORKDIR}/40_custom ${D}${sysconfdir}/grub.d/40_custom
}

FILES_${PN}-doc = "${datadir}"
FILES_${PN} = "/usr /etc"

