SUMMARY = "Intel Wireless LinuxCore 24 kernel driver"
DESCRIPTION = "Intel Wireless LinuxCore 24 kernel driver"
SECTION = "kernel"
LICENSE = "GPLv2"

REQUIRED_DISTRO_FEATURES = "wifi"

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit module

PROVIDES = "kernel-module-cfg80211-${KERNEL_VERSION} \
            kernel-module-mac80211-${KERNEL_VERSION} \
            kernel-module-iwlmvm-${KERNEL_VERSION}   \
            kernel-module-iwlwifi-${KERNEL_VERSION}  \
            kernel-module-compat-${KERNEL_VERSION}"

RPROVIDES_${PN} = "kernel-module-cfg80211-${KERNEL_VERSION} \
                   kernel-module-mac80211-${KERNEL_VERSION} \
                   kernel-module-iwlmvm-${KERNEL_VERSION}   \
                   kernel-module-iwlwifi-${KERNEL_VERSION}  \
                   kernel-module-compat-${KERNEL_VERSION}"

PV = "30"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/iwlwifi/backport-iwlwifi;branch=release/LinuxCore${PV}"
SRC_URI += "file://iwlwifi.conf"

SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

SYSTEMD_AUTO_ENABLE_${PN} = "enable"

CONFFILES_${PN} += "${sysconfdir}/modprobe.d/iwlwifi.conf"

PACKAGES = "${PN}"

RDEPENDS_${PN}="linux-firmware-iwlwifi-8000c"

DEPENDS="virtual/kernel"
KERNVER="${KERNEL_VERSION}"

## modules actual
FILES_${PN} = "/lib/modules/${KERNVER}/updates/cfg80211.ko \
               /lib/modules/${KERNVER}/updates/compat.ko \
               /lib/modules/${KERNVER}/updates/iwlwifi.ko \
               /lib/modules/${KERNVER}/updates/iwlmvm.ko \
               /lib/modules/${KERNVER}/updates/mac80211.ko \
               ${sysconfdir}/modprobe.d/iwlwifi.conf"

EXTRA_OEMAKE = "INSTALL_MOD_PATH=${D} KLIB_BUILD=${KBUILD_OUTPUT}"

do_configure() {
	CC=gcc CFLAGS= LDFLAGS= make defconfig-iwlwifi-public KLIB_BUILD=${KBUILD_OUTPUT}
}

do_install() {
	## install kernel objects from driver tree into target fs
	install -m 0755 -d ${D}${base_libdir}/modules/${KERNVER}/updates/

	install -m 0644 $(find ${S} -name "iwlwifi.ko")  ${D}${base_libdir}/modules/${KERNVER}/updates
	install -m 0644 $(find ${S} -name "iwlmvm.ko")   ${D}${base_libdir}/modules/${KERNVER}/updates
	install -m 0644 $(find ${S} -name "cfg80211.ko") ${D}${base_libdir}/modules/${KERNVER}/updates
	install -m 0644 $(find ${S} -name "mac80211.ko") ${D}${base_libdir}/modules/${KERNVER}/updates
	install -m 0644 $(find ${S} -name "compat.ko")   ${D}${base_libdir}/modules/${KERNVER}/updates

	## install configs and service scripts
	install -d ${D}${sbindir} ${D}${sysconfdir}/modprobe.d
	install -m 0644 ${WORKDIR}/iwlwifi.conf ${D}${sysconfdir}/modprobe.d

	## this gets generated for no good reason. delete it.
	rm -rf ${D}/usr
}
