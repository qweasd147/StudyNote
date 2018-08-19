# Docker

순수 docker의 사용법은 할만한거 같은데 상세 설정(Dockerfile) + 리눅스 환경이 익숙지 않아서 어렵게 느껴진다...

# 1. 정의

### docker
    준비중...

### DockerFile

간단히 Dockerfile은 Docker 이미지 생성을 위한 일종의 배치 파일이라고 이해하면 된다.

Dockerfile에서는 파일 및 디렉토리 추가, 환경변수 지정, 대상 이미지를 사용하여 컨테이너를 시작할 때 어떤 프로세스를 실행 하는지

# 2. docker 실행 옵션

| 옵션 | 설명 |
| ------------- | ------------- |
| -d | detached mode. 백그라운드 모드 |
| -p | 호스트와 컨테이너의 포트를 연결(포워딩) <br /> ex) ... -p 1234:8080 ... 옵션을 주면 1234번 포트로 접속하면 내부 컨테이너의 8080으로 포워딩(연결)하여 준다. |
| -v | 호스트와 컨테이너의 디렉토리를 연결(마운트) |
| -e | 컨테이너 내에서 사용할 환경변수 설정 |
| -name | 컨테이너 이름 설정 |
| -rm | 프로세스 종료 시 컨테이너 자동 제거 |
| -it | i(interactive)옵션과 t(pseudo-tty)옵션을 사용하여 bash 쉘 사용 |
| -link | 컨테이너 연결 [컨테이너명:별칭] |


ex) ubuntu 이미지를 hello 라는 컨테이너로 생성 한 뒤, bash 쉘 실행
$ docker run -i -t --name hello ubuntu /bin/bash
# 3. docker와 docker compose 차이점

    docker compose
    Docker Compose는 컨테이너 여럿을 띄우는 도커 애플리케이션을 정의하고 실행하는 도구(Tool for defining and running multi-container Docker applications)
    컨테이너를 여러개 띄울 시, 순서대로 컨테이너를 띄울 수 있고, run 할때 옵션들을 미리 정의할수도 있다.

| docker | docker-compose | 설명 |
| ------------- | ------------- | ------------- |
| Dockerfile | Dockerfile-dev | 서버 구성을 문서화 <br /> ex) 클래스 선언 |
| docker build | docker-compose build | 도커 이미지 만들기 <br /> ex) 클래스 선언을 어플리케이션에 로드 |
| docker run (+@ 옵션들) | docker-compose.yml | 이미지에 붙이는 장식들. <br /> ex) 인스턴스의 변수들 |
| docker run | docker-compose up | 장식 붙은 이미지를 실제로 실행 <br /> ex) 인스턴스 생성 |

# 4. 참고

#### 도커 무작정 따라하기(책)
http://pyrasis.com/docker/2015/02/09/docker-for-dummies

#### 하루만에 배우는 도커(책)
http://www.hanbit.co.kr/store/books/look.php?p_code=E7149016842

#### 인터넷 클라우드에서 도커 체험
https://labs.play-with-docker.com/

#### docker 설명(블로그)

* http://raccoonyy.github.io/docker-usages-for-dev-environment-setup/
* https://blog.nacyot.com/articles/2014-01-27-easy-deploy-with-docker/
* http://programmingsummaries.tistory.com/392?category=695325