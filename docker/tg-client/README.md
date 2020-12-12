## telegram-client 镜像

telegram client for centOS,基于开源项目 [vysheng/tg](http://github.com/vysheng/tg)

可以登录你的`telegram`, 利用脚本做一些自动化的事情,比如 msg,send_file

**外网环境下才能正常运行,不是外网环境请配置代理后使用**

### 快速启动容器
```text
docker run -itd --name telegram zhule/tg-client /bin/bash
```
### 进入容器并登录账号
```text
docker exec -it telegram bash

1. telegram-cli
2. 输入手机号 eg: +86 188xxxxxxxx
3. 输入验证码登录
```
### 调用脚本发送消息
```text
docker exec telegram /bin/bash -c "tg msg ${@目标用户名} ${消息}"
```


