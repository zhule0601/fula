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
