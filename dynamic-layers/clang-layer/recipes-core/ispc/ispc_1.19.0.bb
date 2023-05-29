SUMMARY  = "Intel(R) Implicit SPMD Program Compiler"
DESCRIPTION = "ispc is a compiler for a variant of the C programming language, \
with extensions for single program, multiple data programming."
HOMEPAGE = "https://github.com/ispc/ispc"

LICENSE  = "BSD-3-Clause & Apache-2.0-with-LLVM-exception"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=da5ecffdd210b3cf776b32b41c182e87 \
                    file://third-party-programs.txt;md5=3cd6f8a7c3bd9d2bb898fcb27c75221a"

inherit cmake python3native ptest

S = "${WORKDIR}/git"

SRC_URI = "git://github.com/ispc/ispc.git;protocol=https;branch=main \
           file://0001-CMakeLists.txt-link-with-libclang-cpp-library-instea.patch \
           file://0002-cmake-don-t-build-for-32-bit-targets.patch \
           file://0001-Fix-QA-Issues.patch \
           file://0001-Add-print-function-to-print-test-run-status-in-ptest.patch \
           file://0001-CMakeLists.txt-allow-to-pick-llvm-config-from-usr-bi.patch \
           file://ffc75e464ff2b8fce7dbf74f1846ebd0852bc6f9.patch \
           file://run-ptest \
           "

SRCREV = "ee43967286215a0511c2bc090e604848b4a32bed"

COMPATIBLE_HOST = '(x86_64).*-linux'

DEPENDS += " clang-native bison-native flex-native"
DEPENDS:append:class-target = " clang"
RDEPENDS:${PN}-ptest += " python3-multiprocessing"

YFLAGS='-d -t -v -y --file-prefix-map=${WORKDIR}=/usr/src/debug/${PN}/${EXTENDPE}${PV}-${PR}'

do_configure:prepend() {
        sed -i -e 's#\${BISON_EXECUTABLE}.*#\${BISON_EXECUTABLE} ${YFLAGS} #g' ${S}/CMakeLists.txt
        sed -i -e 's#\${FLEX_EXECUTABLE}.*#\${FLEX_EXECUTABLE} \-L #g' ${S}/CMakeLists.txt
}

do_install_ptest() {
        cp -rf ${S}/run_tests.py ${D}${PTEST_PATH}
        cp -rf ${S}/common.py ${D}${PTEST_PATH}
        cp -rf ${S}/tests ${D}${PTEST_PATH}
        cp -rf ${S}/test_static.isph ${D}${PTEST_PATH}
        cp -rf ${S}/fail_db.txt ${D}${PTEST_PATH}
        cp -rf ${S}/test_static.cpp ${D}${PTEST_PATH}
}

EXTRA_OECMAKE += " \
                  -DISPC_INCLUDE_TESTS=OFF  \
                  -DISPC_INCLUDE_EXAMPLES=OFF  \
                  -DISPC_NO_DUMPS=ON  \
                  -DARM_ENABLED=OFF  \
                  -DISPC_CROSS=ON  \
                  -DISPC_ANDROID_TARGET=OFF  \
                  -DISPC_FREEBSD_TARGET=OFF  \
                  -DISPC_WINDOWS_TARGET=OFF  \
                  -DISPC_IOS_TARGET=OFF  \
                  -DISPC_PS4_TARGET=OFF  \
                  -DSYSROOT_DIR=${STAGING_DIR} \
                  -DCLANG_EXECUTABLE=${STAGING_BINDIR_NATIVE}/clang \
                  -DCLANGPP_EXECUTABLE=${STAGING_BINDIR_NATIVE}/clang++ \
                  -DLLVM_DIS_EXECUTABLE=${STAGING_BINDIR_NATIVE}/llvm-dis \
                  -DLLVM_AS_EXECUTABLE=${STAGING_BINDIR_NATIVE}/llvm-as \
                  "
BBCLASSEXTEND = "native nativesdk"
