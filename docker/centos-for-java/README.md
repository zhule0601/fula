启动测试的 docker 容器  

预装了 `java, maven, git`等常用 `java` 环境

快速启动并进入容器
`docker run -it zhule/centos_for_java /bin/bash`

启动容器
`docker run -itd zhule/centos_for_java /bin/bash`

进入容器
`docker exec -it ${容器id} bash`

宿主机上执行容器内命令
`docker exec ${容器id} /bin/bash -c "java -version"`


[docker hub](https://hub.docker.com/r/zhule/centos_for_java) 与 [github](https://github.com/zhule0601/fula/docker/centos_for_java) 相关联, 
Dockerfile 更新时,会自动 `build` image

**todo**: docker hub build 时,增加自动测试例
 



