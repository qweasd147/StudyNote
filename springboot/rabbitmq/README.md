## Rabbit MQ (Message Queue)


### DLX(Dead Letter Exchange)

기본적으로 메세지 처리시 에러가 나면 다시 queue로 돌아간다. 만약 절대 처리 할 수 없는 메세지가 수신되어 `Exception`을 계속 던지게 된다면 이는 계속 큐에 남게 된다.
하지만 반복적으로 에러(특정 횟수 이상)가 나면 DLX로 보내 특별한 처리를 유도한다.

application의 retry에서 셋팅이 가능하다.