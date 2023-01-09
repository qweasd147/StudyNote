필요한 셋팅 후(key_pair 2개 등록)

```sh
$ terraform apply
```

# apply 결과 화면 예시

```
alb_dns_name = "tf-alb-xxxxx.ap-northeast-2.elb.amazonaws.com"
bastion-public-ip = "3.xxx.xxx.xxx"
server-private-ip = {
  "web-2a" = "10.0.3.xxx"
  "web-2c" = "10.0.4.xxx"
}
```

# 1. local pc -> bastion ec2

코드 상으로 key pair 이름이 `tf-key-pair` 인 키값을 bastion 서버 접속에 필요한 키로 설정했다. `key_pair`에 있는 프로젝트 그대로 사용 했다면 `tf_key`이 파일이 된다.

밑의 코드들은 해석을 위한 내용들

**upload_pub_key.tf**

```
resource "aws_key_pair" "terraform-key-pair" {
  key_name   = "tf-key-pair"
  public_key = file("/Users/joohyung.kim/.ssh/tf_key.pub")
  tags = {
    Name = "tf-for-test"
  }
}
```

로컬에 있는 `tf_key.pub`를 aws로 올렸음

**vpc_ec2/main.tf**

```
data "aws_key_pair" "for_bastion_key_pair" {
  key_name = "tf-key-pair"
}
```

해당 프로젝트에선 위 키값을 가져와 `bastion` sg로 연결함

로컬에서 바로 `bastion` 서버로 접속 해도 되지만 그냥 `config`에 등록 해놓고 계속 쓴다면

```sh
$ vi ~/.ssh/config
```

이후 하단에 접속 정보 추가

```sh
Host aws-bastion
    HostName 3.xxx.xxx.xxx
    Port 22
    User ec2-user
    IdentityFile ~/.ssh/tf_key
```

여기서 `3.xxx.xxx.xxx`이 값은 출력된 `bastion` 서버 ip가 된다

이후 `$ ssh aws-bastion` 명령어 만으로도 `bastion` 서버 ssh 접속 가능

---

아님 단발성으로 하고 싶다면 그냥 ssh 옵션으로 바로 접속 가능

```sh
$ ssh -i ~/.ssh/tf_key ec2-user@베스천_서버_아이피
```

# 2. bastion -> private ec2

`bastion` 서버 접속 후, 이제 private subnet에 있는 ec2로 접속하기 위해 `bastion`에서 똑같이 ssh 정보를 입력해준다

```sh
Host web-2a
    HostName 출력되는_web-2a_ip
    Port 22
    User ec2-user
    IdentityFile ~/.ssh/tf_private_key
Host web-2c
    HostName 출력되는_web-2c_ip
    Port 22
    User ec2-user
    IdentityFile ~/.ssh/tf_private_key
```

로컬 pc에서 `private ssh key` 파일을 베스천 서버에 `~/.ssh/tf_private_key` 위치로 복붙 해놔야 베스천 -> private ec2(웹서버 들)에 ssh 접속이 가능하다

```sh
$ chmod 644 config
$ chmod 600 tf_private_key
```

보안 적인 요소로 파일 읽기/쓰기 권한을 위와 같이 맞춰줘야 ssh 접속이 가능하다(베스천 서버에서 작업)

#### 만약 위 chmod 안하면 아래와 같이 표출 된다

```
[ec2-user@ip-10-0-1-135 .ssh]$ ssh web-2a
Bad owner or permissions on /home/ec2-user/.ssh/config
```

# 3. Test

이제 셋팅은 다 끝남.

## 3.1 ALB

일반적으로 웹서버 접근은 `LB(alb)`를 통해서만 접근 가능하고, output에 출력 되는 `alb_dns_name`의 도메인 주소로 웹서버 호출 및 확인 가능함.

## 3.2 bastion

베스천 서버 접속은 위에서 설명했고, 설명한 셋팅 그대로 했다면 `private key`만 있다면 어디서든 ssh 접근 할 수 있음 -> 필요하면 ec2 ssh에 ip 제한 해도 됨

또한 `bastion` 서버에서 `web` 서버로 호출도 가능하다

```sh
$ curl web_2a_private_ip
(web_2a_ec2_instance_id출력)

$ curl web_2c_private_ip
(web_2c_ec2_instance_id출력)
```

bastion -> web2로 ssh 접속도 역시 위에서 설명함
