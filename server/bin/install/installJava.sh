#!/bin/bash
# 1. Download tarName to packageDir
# 2. Extract the installation package to installDir
# 3. Configure environment variables
# 4. need exec source /etc/profile by your han
packageDir=/opt/java
installDir=/opt/java
tarName=jdk-8u141-linux-x64.tar.gz

function creaeteDir() {
    rm -rf $packageDir
    if [ ! -d $packageDir ]; then
        mkdir -p $packageDir
    fi

    rm -rf installDir
    if [ ! -d $installDir ]; then
        mkdir -p $installDir
    fi
}

function downloadPackage() {
  wget -P $packageDir \
  --no-cookies --no-check-certificate --header \
  "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" \
  "http://download.oracle.com/otn-pub/java/jdk/8u141-b15/336fa29ff2bb4ef291e347e091f7f4a7/jdk-8u141-linux-x64.tar.gz"

  tar -zxf $packageDir/$tarName -C $installDir --strip-components 1
}

function configureEnvironment() {
    echo "JAVA_HOME=$installDir" >> /etc/profile
    echo "PATH=\$PATH:\$JAVA_HOME/bin" >> /etc/profile
    source /etc/profile
}
function main() {
    creaeteDir
    downloadPackage
    configureEnvironment
}
main

