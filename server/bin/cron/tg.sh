#!/bin/bash

case $1 in
send)
  echo "send_file"
  echo " docker exec telegram /bin/bash -c "tg send_file $2 $3""
  temp_file="/tmp/$(echo $3 | awk -F "/" '{print $NF}')"
  echo "docker cp $3 telegram:$temp_file"
  docker cp $3 telegram:$temp_file
  docker exec telegram /bin/bash -c "tg send $2 $temp_file"
  ;;
msg)
  echo "msg"
  echo " docker exec telegram /bin/bash -c tg msg "$2 $3""
  docker exec telegram /bin/bash -c "tg msg $2 $3"
  ;;
*)
  echo "send|msg"
  ;;
esac

