package thread.items.product;

import java.util.stream.IntStream;

public class Consumer implements Runnable{

    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        IntStream.range(0,11)   //0~10
        .forEach(cnt -> {
            int data = buffer.get();

            System.out.println("소비자 : "+data+"번 데이터 소비");

            try {
                Thread.sleep((int)(Math.random()*10000));
            } catch (InterruptedException e) { e.printStackTrace(); }
        });
    }
}
