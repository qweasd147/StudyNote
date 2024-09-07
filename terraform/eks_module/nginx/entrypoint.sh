#!/bin/sh

# AWS 메타데이터 서비스에서 인스턴스 ID와 호스트명을 가져오기
INSTANCE_ID=$(curl -s http://169.254.169.254/latest/meta-data/instance-id)
HOSTNAME=$(hostname)

# index.html 파일의 placeholder를 실제 값으로 치환
sed -i "s/Loading_INSTANCE_ID/$INSTANCE_ID/" /usr/share/nginx/html/index.html
sed -i "s/Loading_HOSTNAME/$HOSTNAME/" /usr/share/nginx/html/index.html

echo "$INSTANCE_ID"
echo "$HOSTNAME"
echo $(hostname)

# Nginx 실행
exec "$@"