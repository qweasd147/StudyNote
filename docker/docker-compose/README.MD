# docker-compose

yml 문법에 따라 작성되며, 여러 컨테이너를 한번에 실행(`run`) 할 수 있고, 각 컨테이너의 의존관계,

`docker run ~ `명령 시 옵션도 작성이 가능하여 더욱 편하게 실행하게 해준다.

# 1. 옵션 설명

| 옵션 | 설명 |
| ------------- | ------------- |
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

### 2.1 depends_on
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