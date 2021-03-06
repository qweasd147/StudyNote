﻿# Dockerfile


# 1. 옵션 파일 샘플

참고로 소스파일이 없어서 아래 소스로는 build 실패할 것이다.

빌드하고 싶으면 build 경로에 nodejs 소스파일(package.json 포함)이 있어야한다.

```
FROM node

RUN mkdir -p /nodejs

WORKDIR /nodejs

ADD . /nodejs

RUN npm install

ENV NODE_ENV development

EXPOSE 3000

CMD ["npm", "start"]
```

# 2. 옵션 설명

### 2.1 FROM
어떤 이미지로 부터 새로운 이미지를 생성할 지를 정의한다.

ex) FROM node:7.0.1

### 2.2 RUN
그냥 명령어를 실행한다고 생각하면 된다.

### 2.3 WORKDIR
작업 디렉토리를 정의한다. `WORKDIR`를 정의하고 dockerfile을 `build` 시 항상 `WORKDIR`이 기본

베이스가 된다.

예를들어

```sh
WORKDIR /app/base

RUN pwd
```

라는 옵션이 있으면 명령어의 실행 시 /app/base로 고정되어 `/app/base`가 출력 될 것이다.

또한 단일성이 아니라 쭉 고정된 값이니까

```sh
WORKDIR /app/base

RUN pwd

RUN cd /app2/base

RUN pwd
```

이런 식으로 작성해도 의미 없다. 하지만 구지 변화를 주고 싶으면 `RUN cd /app2 && pwd`이런식으로 해야한다.

### 2.4 ADD
그냥 cp 명령어를 날린다고 생각. 보통 앱 코드를 복사하는 용도로 많이 쓰인다.

ex) Dockerfile이 있는 곳을 기준으로, 그 디렉토리에 있는 파일을 특정 디렉토리로 이동
ADD . /nodejs

### 2.5 ENV
환경변수 지정
```
ENV NODE_ENV dev #NODE_ENV라는 환경변수에 dev값을 넣는다.
```

### 2.6 EXPOSE
가상 머신에 오픈할 포트 지정

### 2.7 CMD
마지막으로 실행될 명령어를 지정. 빌드할 때는 실행되지 않고, 컨테이너가 올라갈때 실행된다.

# 3. .dockerignore
Dockerfile과 같은 디렉토리에 들어있는 모든 파일을 context라고 하고, 이미지를 생성할 때 context를

모두 Docker 데몬에 전송한다. 이때 특정 파일을 제거하고 싶으면 .dockerignore파일을 작성하여 같이 두면

ignore가 된다.

# 4. 소스 업데이트

컨테이너를 껏다가 다시 켜야 하는거 같다...

```sh
$docker stop 컨테이너
$docker start 컨테이너

#또는 restart
$docker restart 컨테이너
```