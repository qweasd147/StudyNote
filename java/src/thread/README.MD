# Thread

# 1. 정의
하나의 어플리케이션 안에서 여러가지 작업을 동시에 하는것을 의미. 각각의 작업을

thread라고 불린다. 컴퓨터에서 프로세스(process)와 thread라는 2가지의 실행 단위가 있는데,

차이점은 프로세스는 자신 만의 데이터를 가지지만 스레드들은 동일한 데이터를 공유한다.
    
# 2. 장점

# 3. 주요 사용법

### 3.1 Thread 하위 메소드

| Method  | 설명 |
| ------------- | ------------- |
| Thread | 매개변수가 없는 기본 생성자 |
| Thread(String name) | 이름이 name인 thread 객체 생성 |
| Thread(Runnable target, String name) | Runnable을 구현하는 객체로 부터 스레드를 생성 |
| static int activeCount() | 현재 활동 중인 스레드의 개수를 반환 |
| String getName() | 스레드의 이름을 반환 |
| int getPriority() | 스레드의 수선순위를 반환 |
| void interrupt() | 현재의 스레드를 중단 |
| boolean isInterrupted() | 현재의 스레드가 중단될 수 있는지를 검사 |
| void setPriority(int priority) | 스레드의 우선순위를 지정 |
| void setName(String name) | 스레드의 이름을 지정한다. |
| static void sleep(int milliseconds) | 현재 스레드를 지정된 시간만큼 재운다. |
| void run() | 스레드가 시작될 때 이 메소드가 호출. 스레드가 해야하는 작업을 구현한다. |
| void start() | 스레드를 시작한다. |
| static void yield() | 현재 스레드를 다른 스레드에 양보하게 만든다. |
| void join() | 해당 스레드가 소멸될때까지 기다리게 한다. |

# 4. 주의할점
