#!/bin/bash
# 1. unzip
# 2. copy application.properties
# 3. restart server

base_dir=$(cd "$(dirname "$0")" || exit; pwd)
echo "$base_dir"

tar_name=$(ls -lt "/opt/build" | grep "fula" | grep "tar" | head -n 1 | awk '{print $9}')
fula_name=${tar_name%%.*}
echo "$tar_name"
echo "$fula_name"
tar -zvxf "$base_dir"/"$tar_name" -C "$base_dir"
\cp /opt/build/application.properties "$base_dir"/"$fula_name"/conf/

exec "$base_dir"/"$fula_name"/bin/fula-server.sh restart

# 已经存在则覆盖
ln -snf "$base_dir"/"$fula_name" /root/current
