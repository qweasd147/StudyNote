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

### 3.2 우선순위

어떠한 스레드에 더 많은 양의 실행시간이 주어져, 결과적으로 더 빨리 완료 될지 설정하는 값.

최소 우선순위(1) 부터 보통 우선순위(5), 최대 우선순위(10)까지 설정 가능하고, 메인스레드는 5이다.

또한 메인스레드 내에서 생성하는 스레드의 우선순위는 별다른 입력이 없을 시, 우선순위를 상속 받아,

자동으로 5가 된다. 또한 우선순위 변경은 스레드를 실행 하기 전에만 우선순위 변경이 가능.

    멀티코어라고 해도 프로세서 환경에서 작업 시, 실행시간과 실행 기회를 더 많이 갖게 될 것이라고 기대할 수는 없다.
    이는 os 레벨에서 다른 방식으로 스케쥴링 하기 때문에 어떠한 os에 따라 다른 결과를 얻을 수 있다.
    따라서 os 별 스케쥴링 정책과 jvm 구현을 확인해야 한다. 따라서 환경에 상관없이 처리를 하고 싶으면
    PriorityQueue 등에 우선순위를 저장해 놓고 작업을 처리하는게 나을 수 있다.

### 3.3 데몬 스레드

    다른 일반 스레드의 작업을 돕는 보조적인 역할을 수행하는 스레드.
    일반 스레드가 모두 종료되면 데몬 스레드는 강제적으로 자동 종료됨.
    이유는 보조적인 스레드라 종속된 일반 스레드가 종료되면 의미가 없어지게 된다. 이러한
    점만 빼면 일반 스레드와 다른점이 없음. 또한 setDaemon 메소드는 start 이전에 설정해야함

### 3.4 join(다른 쓰레드의 작업을 기다린다)

    쓰레드 자신이 하던 작업을 잠시 멈추고 다른 쓰레드가 지정된 시간동안 작업을 수행하도록
    할때 join()을 사용한다. 현재 쓰레드가 아닌 특정 쓰레드에 대해 동작하는점이 sleep()과 다르다.
    
```java
    public void baseThread2() throws InterruptedException {

        int triesCount = 0;

        Thread threadForSleep = new Thread(new ConcreteInterface1());
        threadForSleep.start();

        while(threadForSleep.isAlive()){
            printMsg("threadForSleep 아직 살아있음");
            triesCount++;
            threadForSleep.join(3000);  //3초간 스레드 종료를 기다리고, 3초가 지나면 넘어감.

            if(triesCount > 2){
                printMsg("강제 인터럽트");
                threadForSleep.interrupt();
                threadForSleep.join();          //스레드가 종료할때까지 무한 대기
            }
        }

        System.out.println("threadForSleep 스레드 종료");
    }
```

위 소스에서 `join`은 다른 스레드가 종료할때 까지 기다리게 된다. 참고로 sleep과 마찬가지로 대기 중

interrupt가 들어올 수 있으며, interrupt가 들어올 시, exception이 발동된다(위 소스에선 그런거 고려안함) 
    

# 4. 주의할점

### 4.1 독립적인 stack에서 실행됨

    stack trace를 사용시 확인 가능. 따라서 한 스레드가 예외가 발생해서 종료되어도 다른 스레드의 실행에는
    영향을 미치지 않는다. run, start 메소드에 따라 호출 스택이 달라짐
    
* 소스 내 `checkCallStack`메소드를 스레드 `run`으로 호출 시 call stack

    java.lang.Exception: Exception
        at thread.items.CheckCallStack.throwException(CheckCallStack.java:12)
        at thread.items.CheckCallStack.run(CheckCallStack.java:7)
        at java.lang.Thread.run(Thread.java:745)
        at thread.ThreadMain.checkCallStack(ThreadMain.java:133)
        at thread.ThreadMain.main(ThreadMain.java:26)
    Picked up JAVA_TOOL_OPTIONS: -Djava.net.preferIPv4Stack=true

* 소스내 `checkCallStack`메소드를 스레드 `start()`으로 호출 시 call stack

    java.lang.Exception: Exception
    	at thread.items.CheckCallStack.throwException(CheckCallStack.java:12)
    	at thread.items.CheckCallStack.run(CheckCallStack.java:7)
    	at java.lang.Thread.run(Thread.java:745)
    	
`run`을 통해 새로운 스레드 환경이 아닌 일반 메소드로 호출 시, callstack 최하단에 `main` 메소드에서 부터 callstack이 쌓이는 것을 확인 할 수가

있다. 하지만 `start`를 써서 새로운 스레드 환경에서 호출 시, 독립적인 stack으로 옮겨 실행 되므로 callstack의 최하단에는 `main`메소드가 아닌

`Thread`에서 부터 시작하는 것을 확인 할 수가 있다.

### 4.2 한번 실행이 종료된 스레드는 다시 실행 할 수 없다.

### 4.3 sleep은 항상 현재 실행 중인 쓰레드에 대해 작동한다.

`sleep` 메소드는 현재 실행 중인 스레드에 대해서만 반응한다. 따라서 인스턴스에서 호출하는건 의미가 없다(태생이 static method 이다) 