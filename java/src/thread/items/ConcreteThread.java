package thread.items;

public class ConcreteThread extends Thread {
    @Override
    public void run() {
        for (int i=0;i<10;i++){
            System.out.println("BaseThreadWithClazz "+i);
        }
    }
}
