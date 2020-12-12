#!/bin/bash

# shellcheck disable=SC2154
base_dir=$(cd "$(dirname "$0")" || exit; pwd)
echo "$base_dir"
# base variable
# extra variable
mysql_password="123456"
mysql_user="root"
mysql_port=3306
backup_databse_name="fula"

# shellcheck disable=SC2120
function dump() {
  begin_time=$(date "+%s")
  backup_path=$1
  backup_file_name=${backup_path}/$2

  mkdir -p "${backup_path}"
  mysqldump -h 127.0.0.1 -u $mysql_user -P $mysql_port -p$mysql_password --database $backup_databse_name > "${backup_file_name}"
  echo "${backup_file_name}"

  end_time=$(date "+%s")
  cost_time=$(($end_time - begin_time))
  echo "cost $cost_time s"
}

function install_mysql_client() {
  yum makecache fast
  check_results=$(yum list installed | grep -c "mariadb")
  echo "command: $check_results"
  if [[ $check_results -ge 1 ]]
  then
    echo "mysql_client has intalled. ignore yum install"
  else
    yum install -y mariadb.x86_64 mariadb-libs.x86_64
  fi
}

case $1 in

dump)
  install_mysql_client
  dump $2 $3
  ;;
recover)
  echo "mysql -h 127.0.0.1 -uroot -P 3306 -p$mysql_password --database fula < /root/backup/mysql/backup.sql.xxxxxx"
  # dump
  ;;
*)
  echo "dump|recover"
  exit 1
  ;;
esac
