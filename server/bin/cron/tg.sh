#!/bin/bash

case $1 in
send)
  echo "send_file"
  echo "/root/tg/bin/telegram-cli -W -e "send_file $2 $3""
  /root/tg/bin/telegram-cli -W -e "send_file @$2 $3"
  ;;
msg)
  echo "msg"
  echo "/root/tg/bin/telegram-cli -W -e "msg @$2 $3""
  /root/tg/bin/telegram-cli -W -e "msg @glados_network /checkin"
  ;;
*)
  echo "send|msg"
  ;;
esac

