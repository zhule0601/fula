#!/bin/bash

backup_file=$1

if  [ ! -n "$backup_file" ] ;then
    echo "you need input absolute path of backup_file"
else
    echo "the path you input is $backup_file"
    status=`mysql -uroot -p$MYSQL_ROOT_PASSWORD --database $MYSQL_DATABASE < $backup_file`
    echo $?
fi
