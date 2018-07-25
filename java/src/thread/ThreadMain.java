package thread;

public class ThreadMain {

    public static void main(String[] args) {
        ThreadMain threadMain = new ThreadMain();

        //threadMain.baseThread1();
        threadMain.baseThread2();

    }

    public void baseThread1(){
        //1. Runnable 인터페이스를 구현
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
        Thread baseWithClass = new Thread(new BaseThread());
        baseWithClass.start();
        baseWithLambda.start();
    }

    public void baseThread2(){
        Thread myRunnable1 = new Thread(new MyRunnable("A"));
        Thread myRunnable2 = new Thread(new MyRunnable("B"));

        myRunnable1.start();
        myRunnable2.start();
    }
}

class BaseThread implements Runnable {
    @Override
    public void run() {
        for (int i=0;i<10;i++){
            System.out.println("class "+i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class MyRunnable implements Runnable {

    private String name;

    public MyRunnable(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for(int i=0;i<10;i++)
            System.out.println(name + i + " ");
    }
}