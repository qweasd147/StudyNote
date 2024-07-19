## 사전 다운로드 되어야 하는거

- terraform
- awscli
- helm
- argocd cli (`brew install argocd`)

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
$ kubectl config current-context # 현재 클러스터 확인
...
...
```

docker build

```sh
# download nginx image
docker pull nginx:1.23.3
# build
cd ./nginx
docker build --platform linux/amd64 -t aws-container-nginx:1.0.0 .
```

ecr login

```sh
aws ecr get-login-password --region ap-northeast-2 --profile joo | docker login --username AWS --password-stdin {id}.dkr.ecr.ap-northeast-2.amazonaws.com/test_eks-aws-container-nginx
# 로그인 확인
cat ~/.docker/config.json
# ecr 정보 확인
aws ecr describe-repositories --region ap-northeast-2 --profile joo
```

push docker image to ecr

```sh
docker tag aws-container-nginx:1.0.0 {id}.dkr.ecr.ap-northeast-2.amazonaws.com/test_eks-aws-container-nginx:1.0.0
docker push {id}.dkr.ecr.ap-northeast-2.amazonaws.com/test_eks-aws-container-nginx:1.0.0
```

change default namespace

```sh
kubectl config set-context --current --namespace joo
```

apply objects

```sh
kubectl apply -f objects/namespace.yaml
kubectl apply -f objects/deployment.yaml
kubectl apply -f objects/service.yaml
```

delete objects

```sh
kubectl delete -f objects/deployment.yaml
```

확인

```sh
kubectl get deployment -n joo # namespace는 생략 가능
kubectl get deployment -o wide
```

상태 확인

```sh
kubectl describe pod <pod_name>
```

ecr logout

```sh
docker logout {id}.dkr.ecr.ap-northeast-2.amazonaws.com/{repository name}
```

현재 클러스터 확인 및 context clear

```sh
kubectl config get-contexts
kubectl config delete-context eks-cluster-test_eks
```

---

argocd는 작업중(작동x)

argocd 설치

```sh
# create namespace
$ kubectl create namespace argocd
# $ kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/ha/install.yaml # install argocd in 'argocd' namespace
# install argocd in 'argocd' namespace
$ kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/v2.3.0-rc5/manifests/ha/install.yaml
```

-- https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
-- https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/ha/install.yaml

argocd 서비스 노출

```sh
$ kubectl patch svc argocd-server -n argocd -p '{"spec": {"type": "LoadBalancer"}}'
```

```sh
$ --  kubectl get secrets --all-namespaces
```

kubectl -n argocd get secret argocd-secret -o jsonpath="{.data}" | base64 -d; echo
aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin <account-id>.dkr.ecr.us-west-2.amazonaws.com
aws ecr get-login-password --region ap-northeast-2 --profile joo | docker login --username AWS --password-stdin {출력 되는 ecr repository url}
