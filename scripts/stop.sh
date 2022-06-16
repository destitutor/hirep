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

IDLE_PORT=$(find_idle_port)
echo "> $IDLE_PORT번 포트에서 실행 중인 애플리케이션 PID를 확인합니다." >> $DEPLOY_LOG_PATH
IDLE_PID=$(lsof -ti tcp:"$IDLE_PORT")

# 프로세스가 현재 켜져 있으면 종료
if [ -z "$IDLE_PID" ]; then
  echo "> 현재 실행 중인 애플리케이션이 없으므로 종료하지 않습니다." >> $DEPLOY_LOG_PATH
else
  echo "> 현재 애플리케이션 PID $IDLE_PID 을 종료합니다." >> $DEPLOY_LOG_PATH
  # SIGKILL(-9) 보다는 SIGTERM(-15)를 사용하는 것이 좋다. 가능한 정상 종료를 시도하기 때문이다.
  kill -15 "$IDLE_PID"
  sleep 5
fi
