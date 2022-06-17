```sh
Host web-2a
    HostName 출력되는_web-2a_ip
    Port 22
    User ec2-user
    IdentityFile ~/.ssh/tf-private-instance
Host web-2c
    HostName 출력되는_web-2c_ip
    Port 22
    User ec2-user
    IdentityFile ~/.ssh/tf-private-instance
```

```sh
$ chmod 644 config
$ chmod 600 tf-private-instance
```
