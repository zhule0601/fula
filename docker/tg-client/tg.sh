#!/bin/bash

case $1 in
send)
  echo "send_file"
  echo "/opt/tg_client/bin/telegram-cli -W -e "send_file $2 $3""
  /root/tg/bin/telegram-cli -W -e "send_file @$2 $3"
  ;;
msg)
  echo "msg"
  echo "/opt/tg_client/bin/telegram-cli -W -e "msg @$2 $3""
  /opt/tg_client/bin/telegram-cli -W -e "msg @$2 $3"
  ;;
*)
  echo "send|msg"
  ;;
esac

