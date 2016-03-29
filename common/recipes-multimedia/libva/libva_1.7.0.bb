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

HOMEPAGE = "http://www.freedesktop.org/wiki/Software/vaapi"
BUGTRACKER = "https://bugs.freedesktop.org"

SECTION = "x11"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=2e48940f94acb0af582e5ef03537800f"

SRC_URI = "http://www.freedesktop.org/software/vaapi/releases/libva/${BP}.tar.bz2"

SRC_URI[md5sum] = "7309097b790de8dbc9641ed6393eab9f"
SRC_URI[sha256sum] = "a689bccbcc81a66b458e448377f108c057d3eee44a2e21a23c92c549dc8bc95f"

DEPENDS = "libdrm virtual/mesa virtual/libgles1 virtual/libgles2 virtual/egl"

inherit autotools pkgconfig

EXTRA_OECONF = "--disable-dummy-driver"

PACKAGECONFIG ??= "${@base_contains("DISTRO_FEATURES", "x11", "x11", "", d)} \
                   ${@base_contains("DISTRO_FEATURES", "wayland", "wayland", "", d)}"
PACKAGECONFIG[x11] = "--enable-x11,--disable-x11,virtual/libx11 libxext libxfixes"
PACKAGECONFIG[wayland] = "--enable-wayland,--disable-wayland,wayland"

PACKAGES =+ "${PN}-x11 ${PN}-tpi ${PN}-glx ${PN}-egl ${PN}-wayland"

RDEPENDS_${PN}-tpi =+ "${PN}"
RDEPENDS_${PN}-x11 =+ "${PN}"
RDEPENDS_${PN}-glx =+ "${PN}-x11"
RDEPENDS_${PN}-egl =+ "${PN}-x11"

FILES_${PN}-dbg += "${libdir}/dri/.debug"

FILES_${PN}-x11 =+ "${libdir}/libva-x11*${SOLIBS}"
FILES_${PN}-tpi =+ "${libdir}/libva-tpi*${SOLIBS}"
FILES_${PN}-glx =+ "${libdir}/libva-glx*${SOLIBS}"
FILES_${PN}-egl =+ "${libdir}/libva-egl*${SOLIBS}"
FILES_${PN}-wayland =+ "${libdir}/libva-wayland*${SOLIBS}"
