package thread;

import thread.items.ConcreteInterface1;
import thread.items.ConcreteInterface2;
import thread.items.ConcreteThread;

public class ThreadMain {

    public static void main(String[] args) {
        ThreadMain threadMain = new ThreadMain();

        //threadMain.baseThread1();
        try {
            threadMain.baseThread2();
        } catch (InterruptedException e) {e.printStackTrace();}

    }

    /**
     * 기본 구현 방법
     */
    public void baseThread1(){
        //1. Runnable 인터페이스를 익명 클래스로 구현
        Thread baseWithLambda = new Thread(() -> {
            for(int i=0;i<10;i++){
                System.out.println("lambda : "+i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //2. Runnable를 구현한 클래스를 넘겨줌
        Thread baseWithClass = new Thread(new ConcreteInterface1());

        //3. Thread 클래스 재정의
        Thread baseThreadWithClazz = new ConcreteThread();

        baseWithClass.start();
        baseWithLambda.start();
        baseThreadWithClazz.start();


        Thread myRunnable1 = new Thread(new ConcreteInterface2("A"));
        Thread myRunnable2 = new Thread(new ConcreteInterface2("B"));

        myRunnable1.start();
        myRunnable2.start();
    }

    /**
     * interrupt, sleep ,join 사용
     */
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

        System.out.println("메인 스레드 종료");
    }

    private static void printMsg(String msg){
        String threadName = Thread.currentThread().getName();
        int priority = Thread.currentThread().getPriority();

        System.out.println(threadName+"..."+priority+":"+msg);
    }

    public void nonSafe(){
        int onlyZero = 0;


    }
}