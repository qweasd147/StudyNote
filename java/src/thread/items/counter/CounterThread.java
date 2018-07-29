package thread.items.counter;

import static java.lang.Thread.sleep;

public class CounterThread implements Runnable{

    Counter counter;

    public CounterThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for(int i=0;i<10000;i++){
            counter.process();

            if(i % 50 == 0) counter.printData();

            try {
                sleep((int)Math.random()*2);
            } catch (InterruptedException e) {e.printStackTrace();}
        }
    }
}