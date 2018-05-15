# ELK

# 1. 정의

elasticsearch, logstash, kibana의 앞글자만 딴것으로 데이터 수집+처리+저장+시각화 기능을 각 모듈별로 모아놓은

완성된 서비스라고 보면 된다. 마치 APM(apache+php+mysql) 처럼 모아 놓은것이라고 보면 된다.

elasticsaerch는 서버를 올리면 rest api 형식으로 crud가 가능한데, 네트워크 인바운드를 사용해서 물리적인

접근권한을 제한하거나 X-Pack 플러그인을 설치하여 kibana, elasticsearch 접근을 막을 수도 있다.

### 1.1 elasticsearch
nosql 형태로 데이터를 저장하고, 각 필드(RDB에서의 컬럼)에 분석 여부를 결정하여 데이터를 세부 인덱싱하여 저장이

가능한데, 이렇게 한 장점은 검색이 빠르다는 점이 있지만 치명적으로 리소스를 많이 잡아먹는다는 단점이 있다.

하드웨어 성능이 나빠도 안되고 5.x 버전부터는 각 아이디 별로 동시 읽기 가능 파일 수, 메모리 사용량 등을 높게

잡아야지만 사용가능하다(설정 안하면 아예 시작도 못한다).여러 가이드 라인에서도 elasticsearch는 검색용 보조 수단으로

사용하고 데이터를 별도의 파일이나 db에 백업해 놓는것을 추천하고 있다.

단점을 너무 많이 적은 것도 있지만 빠르다는 장점이 위의 단점을 다 없애주고 있고, 요즘 규모가 큰곳에서도

많이 사용하고 있다고 한다. nosql을 기존에 사용해봤다면 사용하는데 많이 편할것이라 생각된다.

### 1.2 logstash
데이터를 수집해서 elasticsearch에 저장하는 역할을 한다. 수집하는 곳은 RDB(sqlite 포함), log file에서 입력 받아

필터링 하여 최종적으로 elasticsearch나 file에 저장이 가능하다. 추가적으로 특정 플러그인 설치 시,

nosql에서도 수집이 가능하다고 한다. 참고적으로 여러곳에서 수집, 처리, 저장 시 빠른 처리를 위해 중간에 redis를

설치하여 사용하기도 한다(한곳에서만 사용 시 별필요 없을것으로 보인다)

※또한 처음 구동 시 clean_run=>true 옵션은 꼭 주자.... 안그럼 :sql_last_value가 예상과 다른 값이 입력 될수도 있다...

### 1.3 kibana
elasticsearch에서 데이터를 가져와 시각화를 해준다. 이게 좋은점은 각 시각화 조건 및 차트를 만들어 저장하고,

그 차트들을 모아 대시보드를 만들고 이 대시보스를 iframe 형식으로 제공되어, 외부에서 바로 볼수가 있다.

당연히 검색 & 표출 조건 등만 저장하고 데이터는 elasticsearch 기반으로 실시간으로 외부로 표출 가능하다.

#### 1.3.1 Discover
데이터를 검색, 검색 결과를 저장할수 있다.
#### 1.3.1 Visualize
저장한 검색 결과 또는 elasticsearch의 index별로 직접 접근하여 각 차트 별로 시각화하여 표출 & 차트를 저장할 수 있다.
#### 1.3.1 Dashboard
Visualize에서 저장한 차트들을 한눈에 볼 수 있도록 말 그대로 대시보드를 만들 수 있다(물론 저장 가능).

# 2. 참고자료
elk를 구축하면서 느낀점은 꽤나 쉽게 구축이 가능하고, 정리가 잘된 참고자료가 많이 있다는 점이다.

그래서 내가 아무리 잘 정리해도 다른곳을 이길 자신이 없어서.... 그냥 구축하면서 어렵거나 어느 범위까지 제공 되는지

위주로 작성하고 참고한 링크를 따로 작성만 하려고한다(elasitcsearch는 개념적으로 어렵다고 생각되어 따로 정리 예정)

* elk 개념 정리 : http://heowc.tistory.com/49
* elk 관련 : https://github.com/higee/elastic
* elk 관련 : https://okdevtv.com/mib/elk/elk5
* es 관련(스크립트 포함) : https://github.com/wikibook/elasticsearch
* es 관련 상세 설명 : https://elasticsearch.oofbird.net/
* logstash 설정 옵션 정보 : http://kangmyounghun.blogspot.kr/2017/08/elasticsearch-snort.html