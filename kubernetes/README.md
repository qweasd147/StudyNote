# Kubernetes

## Object

### pod
여러 개의 컨테이너를 모아서 pod로 관리하고 배포 최소 단위가 된다.

한 pod는 하나의 노드에만 소속 된다.

### label
말 그대로 라벨. 특정 노드에 붙도록 스케쥴링 정책을 유도할 수 있고, 서비스 구성 기준으로도 묶을수 있다.

### node
쿠버네티스에서 하나의 워커머신(가상 머신의 인스턴스가 노드가 된다). Docker 컨테이너를 작동 시키는 서버이고, 여러개의 노드로 클러스트를 구성한다.

### service
쿠버네티스 네트워크를 관리

#### type
* ClusterIP
* NodePort
* Load Balancer

### volume
마운트 하는 역할의 Object. 마운트 할 volume을 관리하여 다른 object에 연결 할 수가 있다.

#### kind
* pv(PersistentVolume)
* pvc(PersistentVolumeClaim)

### ConfigMap
필요한 상수들을 관리. 환경 변수로 관리되며 다른 pod 등에 연결하여 관리가능

### Secret
ConfigMap이랑 비슷하지만 한번 인코딩해서 메모리에 저장된다.
