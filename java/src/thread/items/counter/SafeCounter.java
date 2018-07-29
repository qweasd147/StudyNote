package thread.items.counter;

public class SafeCounter implements Counter{
    private int onlyZero = 0;

    @Override
    public synchronized void increment(){
        onlyZero = onlyZero + 1;
    }

    @Override
    public synchronized void decrement(){
        onlyZero = onlyZero - 1;
    }

    @Override
    public void printData() {
        System.out.println("only zero ? "+onlyZero);
    }

    @Override
    public void process() {
        increment();
        decrement();
    }
}
