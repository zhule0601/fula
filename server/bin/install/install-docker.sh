#!/bin/bash
# 1. installDockerCe
# 2. installDockerCompose
#
#
installDir=/opt/docker
shellName=get-docker.sh

function creaeteDir() {
    rm -rf installDir
    if [ ! -d $installDir ]; then
        mkdir -p $installDir
    fi
}

function installDockerCE() {
    curl -fsSL get.docker.com -o $installDir/$shellName
    sudo sh $installDir/$shellName
    sudo systemctl enable docker
    sudo systemctl start docker
    docker network create zl-bridge
}
function installDockerCompose() {
    sudo curl -L https://github.com/docker/compose/releases/download/1.24.1/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose
}

function main() {
    creaeteDir
    installDockerCE
    #installDockerCompose
}

main
