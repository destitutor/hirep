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
source "$ABS_DIR"/switch.sh

IDLE_PORT=$(find_idle_port)

echo "> Health Check를 실행합니다." >> $DEPLOY_LOG_PATH
echo "> IDLE_PORT: $IDLE_PORT" >> $DEPLOY_LOG_PATH
echo "> curl -s http://localhost:$IDLE_PORT/profile" >> $DEPLOY_LOG_PATH
sleep 10

for RETRY_COUNT in {1..10}
do
  RESPONSE=$(curl -s http://localhost:"$IDLE_PORT"/profile)
  UP_COUNT=$(echo "$RESPONSE" | grep -c "production" | wc -l)

  # "production" 문자열이 있으면 성공 ($UP_COUNT >= 1)
  if [ "$UP_COUNT" -ge 1 ]; then
    echo "> Health Check 성공" >> $DEPLOY_LOG_PATH
    switch_proxy
    break
  else
    echo "> Health Check의 응답을 알 수 없거나 혹은 실행 상태가 아닙니다." >> $DEPLOY_LOG_PATH
    echo "> Health Check의 응답: $RESPONSE" >> $DEPLOY_LOG_PATH
  fi

  if [ "$RETRY_COUNT" -eq 10 ]; then
    echo "> Health Check 실패" >> $DEPLOY_LOG_PATH
    echo "> 엔진엑스에 연결하지 않고 배포를 종료합니다." >> $DEPLOY_LOG_PATH
    exit 1
  fi

  echo "> Health Check에 실패했습니다. 재시도 중..." >> $DEPLOY_LOG_PATH
  sleep 10
done
