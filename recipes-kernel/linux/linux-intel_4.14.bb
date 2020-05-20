require linux-intel.inc

KBRANCH = "4.14/base"
KMETA_BRANCH = "yocto-4.14"

# Fix for 32-bit perf issue. Remove when patch is backported to 4.14.
SRC_URI_append = " file://0001-perf-x86-32-explicitly-include-errno.h.patch \
                   file://0001-menuconfig-check-lxdiaglog.sh-Allow-specification-of.patch \
                   file://0001-perf-tools-Add-Python-3-support.patch \
                   "

DEPENDS += "elfutils-native openssl-native util-linux-native"

LINUX_VERSION ?= "4.14.170"
SRCREV_machine ?= "61037f1658f0f100a3ed1ed9954142525e107cd0"
SRCREV_meta ?= "a889c43359ca8bee705601817c50edf3c209bc09"

# For Crystalforest and Romley
KERNEL_MODULE_AUTOLOAD_append_core2-32-intel-common = " uio"
KERNEL_MODULE_AUTOLOAD_append_corei7-64-intel-common = " uio"

# Functionality flags
KERNEL_EXTRA_FEATURES ?= "features/netfilter/netfilter.scc features/security/security.scc"
