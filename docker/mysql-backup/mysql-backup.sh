#!/bin/bash

begin_time=$(date "+%s")
sufix=$(date "+%Y-%m-%d-%H-%M-%S")

backup_path=/opt/backup
backup_file_name=${backup_path}/backup-$MYSQL_DATABASE-$sufix

mkdir -p "${backup_path}"
mysqldump -uroot -p$MYSQL_ROOT_PASSWORD --database $MYSQL_DATABASE > "${backup_file_name}"
echo "${backup_file_name}"

end_time=$(date "+%s")
cost_time=$(($end_time - begin_time))
echo "cost $cost_time s"
