# 1. key pair 생성

```sh
$ ssh-keygen -t <타입> -b <크기> -C "<설명>"
```

- t "Type" type key 유형. 기본값이 rsa
- b "Bits" 키의 크기 지정 rsa는 경우 최소 2048비트 이상을 추천
- C "Comment" 키의 설명을 입력

입력하면 차례대로 파일명, 비밀번호를 입력하라는 인터페이스가 나온다.

### 1.1 비밀번호 지정은 자유(당연히 하면 보안적으로 좋음)

### 1.2 코멘트는 누구 key인지 식별 할 수 있는 정보

한 서버에 공개키는 여러개 등록 될 수가 있는데, 나중에 이 코멘트 정보로 어떤 공개키 인지 식별하기 위해 쓰인다. 로컬 공개키라면 이메일이나 hostname 등을 많이 쓴다.

# 2. 공개키 서버(ec2 등) 등록

접근 가능한 관리자가 `~/.ssh/authorized_keys` 파일에 붙여 넣어도 상관 없지만, `ssh-copy-id` 명령어를 통해 로컬 pc에서 손쉽게 등록이 가능하다

관리자가 공개키를 받은 후, 로컬(개인 pc)에서 서버(ec2 등)에 공개키 등록

```sh
$ ssh-copy-id -i "공개키_파일_경로" 사용자@ec2_ip
```

# 3. 비밀번호 키 체인에 등록

비밀번호를 설정하면 ssh를 쓸 때는 물론이고, 다른 서트파티에서도 비밀번호를 요구, 이런 인터페이스가 없는 서드파티는 에러가 발생 할 수도 있다.
그래서 key chain을 걸어 두면 일단 한번 설정하면 비밀번호를 매번 입력하지 않아도 된다.

이 작업은 당연 개인 로컬 pc에 설정을 걸어 두어야 한다.

```sh
$ cd ~/.ssh
$ vi config
```

config 파일 생성 or 편집기 연 후, 아래 내용을 상단에 붙여넣으면 된다.

```sh
Host *
    UseKeychain yes
    AddKeysToAgent yes
```

공개키 등록도 일단 뭐 terraform으로 등록하려고 한거긴 한데, 한번 등록하고 더이상 state를 관리하고 싶지 않으면(등록만 terraform, 관리는 안하겠단 의미) 아래 명령어로 state를 다 지워도 된다.

```sh
$ terraform state rm aws_key_pair.terraform-key-pair
```

물론 경로는 조심해야한다.

# 4. 개인 설정

해당 프로젝트에서 사용 되는 ssh 키는 아래와 같이 셋팅함

```sh
$ ssh-keygen -t rsa -b 2048 -C "이메일or코멘트 등등"
(파일명은 tf_key)

# 만들어진 key pair를 aws 에 등록.
$ terraform apply

# 상태 삭제
$ terraform state rm aws_key_pair.terraform-key-pair
```
