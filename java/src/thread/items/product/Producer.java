package thread.items.product;

import java.util.stream.IntStream;

public class Producer implements Runnable{

    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        IntStream.range(0,11)   //0~10
        .forEach(cnt -> {
            buffer.put(cnt);

            //System.out.println("생산자 : "+cnt+"번 데이터 생산");

            try {
                Thread.sleep((int)(Math.random()*10));
            } catch (InterruptedException e) { e.printStackTrace(); }
        });
    }
}
