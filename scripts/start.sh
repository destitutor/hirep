#!/bin/bash

PROJECT_ROOT_PATH=/home/ubuntu/server/deploy
JAR_PATH=$PROJECT_ROOT_PATH/hireo-springboot-webservice.jar

APP_LOG_PATH=$PROJECT_ROOT_PATH/logs/app.log
ERROR_LOG_PATH=$PROJECT_ROOT_PATH/logs/error.log
DEPLOY_LOG_PATH=$PROJECT_ROOT_PATH/logs/deploy.log

# 재귀적으로 들어가서 실제 파일의 경로를 찾는다.
ABS_PATH=$(readlink -f "$0")
# 현재 stop.sh가 속해 있는 경로를 찾는다.
# dirname은 전체 경로의 파일명에서 디렉토리가 아닌 접미사를 제거하는 명령어다.
ABS_DIR=$(dirname "$ABS_PATH")
# 후행에 오는 파일을 읽어서 파일 속의 내용을 실행하는 역할이다. 자바의 import와 유사하다.
source "$ABS_DIR"/profile.sh

echo "> 빌드 파일 복사" >> $DEPLOY_LOG_PATH
cp $PROJECT_ROOT_PATH/build/libs/*.jar $JAR_PATH

IDLE_PROFILE=$(find_idle_profile)

# jar 파일 실행
echo "> $JAR_PATH 를 profile=$IDLE_PROFILE 로 실행합니다." >> $DEPLOY_LOG_PATH
nohup java -jar \
 -Dspring.config.location=file:$PROJECT_ROOT_PATH/application.yml, \
 file:$PROJECT_ROOT_PATH/application-"$IDLE_PROFILE".yml, \
 file:$PROJECT_ROOT_PATH/application-oauth.yml, \
 file:$PROJECT_ROOT_PATH/application-db.yml, \
 -Dspring.profiles.active="$IDLE_PROFILE", \
 $JAR_PATH > $APP_LOG_PATH 2> $ERROR_LOG_PATH &
