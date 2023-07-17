SUMMARY = "The Intel(R) Graphics Compute Runtime for OpenCL(TM)"
DESCRIPTION = "The Intel(R) Graphics Compute Runtime for OpenCL(TM) \
is an open source project to converge Intel's development efforts \
on OpenCL(TM) compute stacks supporting the GEN graphics hardware \
architecture."

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=eca6ec6997e18db166db7109cdbe611c \
                    file://third_party/opencl_headers/LICENSE;md5=dcefc90f4c3c689ec0c2489064e7273b"

SRC_URI = "git://github.com/intel/compute-runtime.git;protocol=https;branch=releases/23.17 \
           file://disable-werror.patch \
           file://add-missing-cstdint-header-to-fix-compile-with-gcc13.patch \
           file://allow-to-find-cpp-generation-tool.patch \
           file://external-ocloc.patch \
           "

SRCREV = "0bb5b3408e6cb61b477e7cad296fd278b11e73be"

S = "${WORKDIR}/git"

DEPENDS += " intel-graphics-compiler gmmlib libva qemu-native"

RDEPENDS:${PN} += " intel-graphics-compiler gmmlib"

inherit cmake pkgconfig qemu

COMPATIBLE_HOST = '(x86_64).*-linux'
COMPATIBLE_HOST:libc-musl = "null"

EXTRA_OECMAKE = " \
                 -DIGC_DIR=${STAGING_INCDIR}/igc \
                 -DBUILD_TYPE=Release \
                 -DSKIP_UNIT_TESTS=1 \
                 -DCCACHE_ALLOWED=FALSE \
                 -DNEO_DISABLE_LD_LLD=ON \
                 -DNEO_DISABLE_LD_GOLD=ON \
                 "

EXTRA_OECMAKE:append:class-target = " \
                                     -Docloc_cmd_prefix=ocloc \
                                     -DCMAKE_CROSSCOMPILING_EMULATOR=${WORKDIR}/qemuwrapper \
                                     "

PACKAGECONFIG ??= ""
PACKAGECONFIG[levelzero] = "-DBUILD_WITH_L0=ON, -DBUILD_WITH_L0=OFF, level-zero"

do_configure:prepend:class-target () {
    # Write out a qemu wrapper that will be used by cmake.
    qemu_binary="${@qemu_wrapper_cmdline(d, d.getVar('STAGING_DIR_HOST'), [d.expand('${B}/bin'),d.expand('${STAGING_DIR_HOST}${libdir}'),d.expand('${STAGING_DIR_HOST}${base_libdir}')])}"
    cat > ${WORKDIR}/qemuwrapper << EOF
#!/bin/sh
$qemu_binary "\$@"
EOF
    chmod +x ${WORKDIR}/qemuwrapper
}

FILES:${PN} += " \
                 ${libdir}/intel-opencl/libigdrcl.so \
                 ${libdir}/libocloc.so \
                 "

FILES:${PN}-dev = "${includedir}"

UPSTREAM_CHECK_GITTAGREGEX = "(?P<pver>\d+(\.\d+)+)"
