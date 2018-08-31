# docker-compose


# 1. 옵션 설명

### 1.1 services
실행하려는 서비스(컨테이너)들을 정의한다.

### 1.1 image
사용 할 이미지

### 1.2 build
빌드할 파일

#### 1.2.1 context
docker build 명령을 실행 할 디렉토리 경로
#### 1.2.2 dockerfile
도커 이미지를 빌드할 docker file

### 1.3 environment
환경변수 설정

### 1.4 ports
포트 설정

### 1.5 depends_on
의존 관계 설정. A->B->C 순으로 컨테이너가 올라가야 한다면 설정한다.

ex) 현재 이미지가 app01이 먼저 실행 된 다음 돌아간다.
    depends_on
        app01

#### 1.5.1 condition
의존 컨테이너가 완전히 올라갔는지 체크

### 1.6 links
컨테이너 간의 통신을 위해 말 그대로 link 시킨다.

만약 App01(백엔드 어플리케이션)과 DB01 컨테이너 끼리 연동하여 통신하여야 한다면

물론 완전히 빠져나와 원격으로 통신할 수 있지만 원격이 아닌 로컬 리눅스 안을 벗어나지 않고도

통신이 가능하다.

### 1.7 command
Dockerfile의 CMD와 똑같다.
(컨테이너가 올라간 후 명령 실행)

### 1.8 volumes
컨테이너를 올릴때(`run`) -v옵션과 똑같다.

### 1.9 healthcheck
컨테이너가 다 올라갔는지 체크하는거. 1.5에서 `depends_on`과 연계된다.