# docker-compose

yml 문법에 따라 작성되며, 여러 컨테이너를 한번에 실행(`run`) 할 수 있고, 각 컨테이너의 의존관계,

`docker run ~ `명령 시 옵션도 작성이 가능하여 더욱 편하게 실행하게 해준다.

# 1. 옵션 설명

| 옵션 | 설명 |
| ------------- | ------------- |
| version | 파일 버전을 명시한다.(2. 옵션 상세 설명 참고) |
| services | 실행하려는 서비스(컨테이너)들을 정의한다. |
| image | 사용 할 이미지 |
| build | 빌드할 파일 |
| context | docker build 명령을 실행 할 디렉토리 경로 |
| dockerfile | 도커 이미지를 빌드할 docker file |
| environment | 도커 이미지를 빌드할 docker file |
| ports | 포트 설정 |
| depends_on | 의존 관계 설정. A->B->C 순으로 컨테이너가 올라가야 한다면 설정한다. |
| condition | 의존 컨테이너가 완전히 올라갔는지 체크 |
| links | 컨테이너 간의 통신을 위해 말 그대로 link 시킨다. |
| command | Dockerfile의 CMD와 똑같다. <br />(컨테이너가 올라간 후 명령 실행) |
| volumes | 컨테이너를 올릴때(`run`) -v옵션과 똑같다. |
| healthcheck | 컨테이너가 다 올라갔는지 체크하는거. 1.5에서 `depends_on`과 연계된다. |

# 2. 옵션 상세 설명

### 2.1 version

현재 작성한 파일을 어느 버전으로 작성했는지 작성한다. 자세한건 레퍼런스 참고

* https://docs.docker.com/compose/compose-file/compose-versioning/

### 2.2 depends_on
의존 관계 설정. A->B->C 순으로 컨테이너가 올라가야 한다면 설정한다.

ex) 현재 이미지가 app01이 먼저 실행 된 다음 돌아간다.
    depends_on
        app01

#### 2.2.1 condition
의존 컨테이너가 완전히 올라갔는지 체크

### 2.3 links
컨테이너 간의 통신을 위해 말 그대로 link 시킨다.

만약 App01(백엔드 어플리케이션)과 DB01 컨테이너 끼리 연동하여 통신하여야 한다면

물론 완전히 빠져나와 원격으로 통신할 수 있지만 원격이 아닌 로컬 리눅스 안을 벗어나지 않고도

통신이 가능하다.

# 3. 명령어

### 3.1 up, down

docker-compose.yml 내에 묶여있는 컨테이너 한번에 구동, 종료, 상태보기

따로 경로 지정을 안하면 현재 디렉토리의 docker-compose.yml를 기준으로 잡는다.

(참고 -d는 백그라운드로 돌리겠다는 옵션)
```sh
docker-compose up -d

docker-compose down

docker-compose ps

```

### 3.2 logs

각 컨테이너의 로그를 볼 수 있다. -f는 리눅스의 `tail -f`와 같은 의미(옵션)

```sh
docker-compose logs
#docker-compose logs -f
```
