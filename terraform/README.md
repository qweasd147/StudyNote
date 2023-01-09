## 테라폼 설치

### 1.1 tfenv 설치

로컬에서 테라폼 버전 관리(node에서 `nvm`, `n`과 같은 역할)

```sh
$ brew install tfenv
```

### 1.2 tfenv로 테라폼 버전관리

```sh
$ tfenv list-remote # 설치 가능한 테라폼 버전 확인
$ tfenv install x.y.z # 해당 테라폼 버전 설치
$ tfenv use x.y.z # 해당 버전 활성화

$ terraform --version # 설치 된 테라폼 버전 확인
```

### 1.3 credentials 셋팅(옵션)

`credentials` 셋팅은 대부분 똑같을 수도 있겠지만 혹시나 다른 중요한 `credentials`(회사 중요 계정 등) 섞이는 걸 방지하기 위해 개인적으로 아예 분리해서 씀

1. `aws` -> `iam`에서 사용자 생성 및 권한 생성
2. 원하는 위치에 `credentials` 파일 생성 및 key 입력

> 보통 `~/.aws/credentials`에 작성하는데 개인적으로 `~/.aws/credentials_joo`에 작성 및 `profile`도 구분하여 사용

`~/.aws/credentials`

```
[joo]
aws_access_key_id=발급받은_access_key
aws_secret_access_key=발급받은_secret_key
```

개인 설정은 다를 수 있겠지만 `profile` 정도는 구분해서 사용하는걸 추천. 그리고 `aws cli` 를 통해 만들어도 편하긴 함

### 1.4 key pair(옵션)

자주 사용 할 수 도 있는 공개키 등록을 위해 필요하면 `key_pair/README.md` 읽고 셋팅

```
alb_dns_name = "tf-alb-300436282.ap-northeast-2.elb.amazonaws.com"
bastion-public-ip = "15.165.17.155"
server-private-ip = {
  "web-2a" = "10.0.3.82"
  "web-2c" = "10.0.4.26"
}
```
