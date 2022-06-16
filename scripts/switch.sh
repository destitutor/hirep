#!/usr/bin/env bash

PROJECT_ROOT_PATH=/home/ubuntu/server/deploy
DEPLOY_LOG_PATH=$PROJECT_ROOT_PATH/logs/deploy.log

# 재귀적으로 들어가서 실제 파일의 경로를 찾는다.
ABS_PATH=$(readlink -f "$0")
# 현재 stop.sh가 속해 있는 경로를 찾는다.
# dirname은 전체 경로의 파일명에서 디렉토리가 아닌 접미사를 제거하는 명령어다.
ABS_DIR=$(dirname "$ABS_PATH")
# 후행에 오는 파일을 읽어서 파일 속의 내용을 실행하는 역할이다. 자바의 import와 유사하다.
source "$ABS_DIR"/profile.sh

function switch_proxy() {
  IDLE_PORT=$(find_idle_port)

  echo "> 전환할 포트: $IDLE_PORT" >> $DEPLOY_LOG_PATH
  echo "> 포트 전환" >> $DEPLOY_LOG_PATH
  echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee -a /etc/nginx/conf.d/service-url.inc

  echo "> nginx 다시 로딩" >> $DEPLOY_LOG_PATH
  sudo service nginx reload
}