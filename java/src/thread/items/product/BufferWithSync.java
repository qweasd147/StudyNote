package thread.items.product;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BufferWithSync implements Buffer{

    private int data;
    private boolean empty = true;

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

    @Override
    public synchronized int get() {
        while (empty){
            try {
                wait(); //생산될 때 까지 기다린다.
            } catch (InterruptedException e) {  e.printStackTrace(); }
        }

        System.out.println("데이터 소비 :"+data+". "+dateFormat.format(new Date())+"   ");

        empty = true;
        notifyAll();

        return data;
    }

    @Override
    public synchronized void put(int data){
        while (!empty){
            try {
                wait(); //소비 될 때까지 기다린다.
            } catch (InterruptedException e) { e.printStackTrace(); }
        }

        System.out.println("데이터 생산 :"+data+". "+dateFormat.format(new Date())+"   ");

        empty = false;
        this.data = data;

        notifyAll();
    }
}
