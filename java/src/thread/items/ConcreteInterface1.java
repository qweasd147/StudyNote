package thread.items;

public class ConcreteInterface1 implements Runnable{
    @Override
    public void run() {
        for (int i=0;i<10;i++){
            System.out.println("class "+i);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                String threadName = Thread.currentThread().getName();
                int priority = Thread.currentThread().getPriority();

                System.out.println(threadName+"..."+priority+":인터럽트가 들어옴");
                System.out.println(e.getMessage());
            }
        }
    }
}
