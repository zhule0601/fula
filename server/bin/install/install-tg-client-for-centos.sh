#!/bin/bash
#######
# dir /opt/tg_client
# 1. git clone
# 2. yum install
# 3. ./configure
# 4. make
# 5. https://my.telegram.org generator ak
#
#######

function create_dir() {
    dir=$1
    rm -rf "$dir"
    if [ ! -d "$dir" ]; then
        mkdir -p "$dir"
    fi
}

install_dir=/opt/tg_client
git_command="git clone --recursive http://github.com/vysheng/tg.git $install_dir"

function main() {
    yum install -y gcc make git readline-devel libconfig-devel jansson-devel openssl-devel libevent-devel lua-devel
    create_dir $install_dir
    exec $git_command
#    cd $install_dir && ./configure && make
}
main
