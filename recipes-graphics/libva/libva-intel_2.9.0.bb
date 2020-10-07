SUMMARY = "Video Acceleration (VA) API for Linux"
DESCRIPTION = "Video Acceleration API (VA API) is a library (libVA) \
and API specification which enables and provides access to graphics \
hardware (GPU) acceleration for video processing on Linux and UNIX \
based operating systems. Accelerated processing includes video \
decoding, video encoding, subpicture blending and rendering. The \
specification was originally designed by Intel for its GMA (Graphics \
Media Accelerator) series of GPU hardware, the API is however not \
limited to GPUs or Intel specific hardware, as other hardware and \
manufacturers can also freely use this API for hardware accelerated \
video decoding."

HOMEPAGE = "https://01.org/linuxmedia/vaapi"
BUGTRACKER = "https://github.com/intel/libva/issues"

SECTION = "x11"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=2e48940f94acb0af582e5ef03537800f"

SRC_URI = "https://github.com/intel/libva/releases/download/${PV}/libva-${PV}.tar.bz2"
SRC_URI[sha256sum] = "e344c1392dde92696c9ffd9cb3c7277d0a3b912236eb4e0fdedf7f375434584b"

S = "${WORKDIR}/libva-${PV}"

UPSTREAM_CHECK_URI = "https://github.com/intel/libva/releases"

DEPENDS = "libdrm"

inherit meson pkgconfig

PACKAGECONFIG ??= " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11 opengl', 'glx', '', d)} \
    ${@bb.utils.filter('DISTRO_FEATURES', 'x11 wayland', d)} \
"

PACKAGECONFIG[x11] = "-Dwith_x11=yes,-Dwith_x11=no,virtual/libx11 libxext libxfixes"
PACKAGECONFIG[glx] = "-Dwith_glx=yes,-Dwith_glx=no,virtual/mesa"

PACKAGECONFIG[wayland] = "-Dwith_wayland=yes,-Dwith_wayland=no,wayland-native wayland"

PACKAGES =+ "${PN}-x11 ${PN}-glx ${PN}-wayland"

RDEPENDS_${PN}-x11 =+ "${PN}"
RDEPENDS_${PN}-glx =+ "${PN}-x11"

FILES_${PN}-x11 =+ "${libdir}/libva-x11*${SOLIBS}"
FILES_${PN}-glx =+ "${libdir}/libva-glx*${SOLIBS}"
FILES_${PN}-wayland =+ "${libdir}/libva-wayland*${SOLIBS}"

PROVIDES += "libva"
RPROVIDES_${PN} += "libva"
RPROVIDES_${PN}-x11 += "libva-x11"
RPROVIDES_${PN}-glx += "libva-glx"
RPROVIDES_${PN}-wayland += "libva-wayland"
