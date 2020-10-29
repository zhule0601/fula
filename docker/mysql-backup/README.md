## 具有`backup`,`restore` 功能的 `mysql:5.6` 镜像

### 快速启动 `mysql` 容器

```text
docker run -d -e MYSQL_ROOT_PASSWORD="123456" -e MYSQL_DATABASE=test -v "/tmp/mysql/backup":/opt/backup -p 3306:3306 zhule/mysql-backup
```

常用参数:
- `MYSQL_ROOT_PASSWORD`: root 用户的密码
- `MYSQL_DATABASE`: 自动创建的database (备份,恢复的数据库)
- `-v`: 挂载到宿主机的备份目录

### backup

```text
docker exec ${容器id} /bin/bash -c "mysql-backup"
```

### restore

```text
docker exec ${容器id} /bin/bash -c "mysql-restore ${备份 sql 的绝对路径}"
```
