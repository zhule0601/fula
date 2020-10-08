#!/bin/bash
# a common docker appctl shell script
# include appctl.sh build|start|stop|rm|rmi

# shellcheck disable=SC2154
base_dir=$(cd "$(dirname "$0")" || exit; pwd)
echo "$base_dir"

# base variable
root_dir=/opt
app_dir="${root_dir}"/mysql
data_dir="${app_dir}"/data
backup_dir="${app_dir}"/backup

#docker
image_name=zlsql
image_tag=1.0
container_name=zlSql
network="zl-bridge"

# extra variable
mysql_password=123456

function cerate_dir() {
  # rm -rf $1
  if [ ! -d "$1" ]; then
    mkdir -p "$1"
    if [ $? = 1 ]; then
      echo "mkdir $1 failure!"
      exit 1
    fi
  fi
}

# shellcheck disable=SC2120
function dump() {
  begin_time=$(date "+%s")

  backup_path=$1
  backup_file_name=${backup_path}/$2

  mkdir -p "${backup_path}"
  mysqldump -h 127.0.0.1 -u root -P 3306 -p$mysql_password --database fula > "${backup_file_name}"
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
build)
  docker build -t "$image_name:$image_tag" .
  ;;
start)
  cerate_dir "$root_dir"
  cerate_dir "$app_dir"
  cerate_dir "$data_dir"
  cerate_dir "$backup_dir"
  docker run --network $network \
    --name "${container_name}" \
    -d \
    -e MYSQL_ROOT_PASSWORD="${mysql_password}" \
    -e MYSQL_DATABASE=inst1 \
    -v "$data_dir":/var/lib/mysql \
    -p 3306:3306 "$image_name:$image_tag"
  # -v $mysqlConfig/my.cnf:/etc/mysql/my.cnf
  ;;
stop)
  docker stop "$container_name"
  ;;
rm)
  docker rm "$container_name"
  ;;
rmi)
  docker rmi "$image_name:$image_tag"
  ;;
dump)
  install_mysql_client
  dump $2 $3
  ;;
recover)
  echo "mysql -h 127.0.0.1 -uroot -P 3306 -p$mysql_password --database fula < /root/backup/mysql/backup.sql.xxxxxx"
  # dump
  ;;
*)
  echo "build|start|stop|rm|rmi"
  exit 1
  ;;
esac
