#!/bin/bash

case $1 in
send)
  echo "send_file"
  echo "/opt/tg_client/bin/telegram-cli -W -e "send_file $2 $3""
  docker exec telegram /bin/bash -c "send_file @$2 $3"
  ;;
msg)
  echo "msg"
  echo "/docker exec telegram /bin/bash -c tg msg "@$2 $3""
  docker exec telegram /bin/bash -c "tg msg @$2 $3"
  ;;
*)
  echo "send|msg"
  ;;
esac

