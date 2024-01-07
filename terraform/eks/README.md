## 사전 다운로드 되어야 하는거

- terraform
- awscli
- helm

1. terraform resource init

```sh
# terraform resource init
$ terraform init
# resource provisioning
$ terraform apply
```

2. 클러스터에 접근 할 수 있도록 kubeconfig 생성

출력 되는 eks-cluster-name을 기준. 다른 변경 없이 그대로 실행하면 `eks-cluster-test_eks`가 출력됨

```sh
aws eks update-kubeconfig --region ap-northeast-2 --profile joo --name eks-cluster-test_eks --alias eks-cluster-test_eks
```

생성이 완료됬다는 메세지와 경로가 뜨면 로컬로 sync된 내역을 볼 수 있음

```sh
$ cat ~/.kube/config
...
...
```
