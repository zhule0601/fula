# fula

## 项目结构

- 后端 springboot
- 数据库 mysql (docker部署) 
- 前端 angular

## 打包部署

- 使用 `maven assembly` 将项目打成 `tar.gz` 压缩包
- 使用 `github actions` + `mvn test` 进行自动化测试
- 使用 `github actions` 自动打包并部署到服务器

## feature

- 微信公众号回复消息
- telegram bot 回复消息
- telegram client 自动发送消息
- 数据库文件自动备份到 telegram bot

## util

- httpclient
- 执行命令,脚本
- 发送 email

## docker 镜像

- centos-for-java
- mysql-backup
- tg-client


## 工具

- idea plugins
    - [maven-helper](https://plugins.jetbrains.com/plugin/7179-maven-helper) **依赖冲突检查**
    - [request-mapper](https://plugins.jetbrains.com/plugin/9567-request-mapper) **快速查找 controller url**
    - [restfultoolkit](https://plugins.jetbrains.com/plugin/10292-restfultoolkit) **快速测试 controller uri**
    - [translation](https://plugins.jetbrains.com/plugin/8579-translation) **翻译**
    - [free-mybatis-plugin](https://plugins.jetbrains.com/plugin/8321-free-mybatis-plugin) **代码生成, xml 跳转**

- server.http
