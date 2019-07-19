SUMMARY = "Intel(R) Media SDK for hardware accelerated media processing"
DESCRIPTION = "Intel(R) Media SDK provides an API to access hardware-accelerated \
video decode, encode and filtering on Intel® platforms with integrated graphics."

HOMEPAGE = "https://github.com/Intel-Media-SDK/MediaSDK"
BUGTRACKER = "https://github.com/Intel-Media-SDK/MediaSDK/issues"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3cb331af679cd8f968bf799a9c55b46e"

# Only for 64 bit until media-driver issues aren't fixed
COMPATIBLE_HOST = '(x86_64).*-linux'

DEPENDS += "libdrm libva intel-media-driver"

PACKAGECONFIG ??= "${@bb.utils.contains("DISTRO_FEATURES", "x11", "dri3", "", d)} \
                   ${@bb.utils.contains("DISTRO_FEATURES", "wayland", "wayland", "", d)}"

PACKAGECONFIG[dri3] 	= "-DENABLE_X11_DRI3=ON, -DENABLE_X11_DRI3=OFF"
PACKAGECONFIG[wayland]	= "-DENABLE_WAYLAND=ON, -DENABLE_WAYLAND=OFF, wayland wayland-native"

SRC_URI = " \
            git://github.com/Intel-Media-SDK/MediaSDK.git;protocol=https;branch=${BPN}-19.2 \
            "

SRCREV = "99e9cb5ef23c564baae51f9536aa93d6c941c4d3"
S = "${WORKDIR}/git"

UPSTREAM_CHECK_GITTAGREGEX = "^intel-mediasdk-(?P<pver>(\d+(\.\d+)+))$"

inherit cmake pkgconfig

EXTRA_OECMAKE += "-DMFX_INCLUDE=${S}/api/include -DBUILD_SAMPLES=OFF"

FILES_${PN} += " \
                 ${libdir}/mfx \
                 ${datadir}/mfx/plugins.cfg \
                 "