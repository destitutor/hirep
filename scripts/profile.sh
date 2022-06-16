#!/usr/bin/env bash

# 쉬고 있는 profile 찾기
# production1이 사용 중이면 production2가 쉬고 있고, 반대면 production1이 쉬고 있다.
function find_idle_profile() {
  # 출력이 필요 없으므로 -o /dev/null로 보낸다.
  # 진행률이나 오류 메시지 역시 필요 없으므로 -s(slient)로 끈다.
  # 응답에서 상태 코드(http_code)의 포맷에 맞는 데이터를 출력한다(-w).
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

  # RESPONSE_CODE >= 400. (4xx, 5xx 에러 모두 포함)
  if [ "$RESPONSE_CODE" -ge 400 ]; then
    CURRENT_PROFILE=production2
  else
    CURRENT_PROFILE=$(curl -s http://localhost/profile)
  fi

  # IDLE_PROFILE은 엔진엑스(nginx)랑 연결되지 않은 프로필이다.
  if [ "$CURRENT_PROFILE" == production1 ]; then
    IDLE_PROFILE=production2
  else
    IDLE_PROFILE=production1
  fi

  # bash라는 스크립트는 값을 반환하는 기능이 없다.
  # 따라서 제일 마지막 줄에 echo로 결과를 출력하고, 클라이언트에서 그 값을 잡아서 사용한다.
  echo "$IDLE_PROFILE"
}

# 쉬고 있는 profile의 포트 찾기
function find_idle_port() {
  IDLE_PROFILE=$(find_idle_profile)

  if [ "$IDLE_PROFILE" == production1 ]; then
    echo "8081"
  else
    echo "8082"
  fi
}