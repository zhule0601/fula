
## docker 镜像

与 [docker hub](https://hub.docker.com/u/zhule) 相关联, Dockerfile 修改时, trigger auto build

ps: github actions 构建镜像并且上传到dockerhub的方式, 上传镜像速度较慢

### centos-for-java

构建 `centos` 测试容器,附带 `java,maven,git` 环境

### mysql-backup

基于`mysql:5.6`镜像, 并提供了`backup`,`restore`功能

### tg-client

基于开源项目 [vysheng/tg](http://github.com/vysheng/tg), 快速启动`tg-client`并登录, 利用脚本发送消息,传输文件等
