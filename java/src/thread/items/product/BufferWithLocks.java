package thread.items.product;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferWithLocks implements Buffer{

    private final Lock lock = new ReentrantLock();
    private final Condition putCond = lock.newCondition();
    private final Condition getCond = lock.newCondition();

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

    private volatile int data;
    private volatile boolean empty = true;

    @Override
    public int get() {
        lock.lock();
        try{
            while(empty){
                putCond.await();
            }

            System.out.println("데이터 소비 :"+data+". "+dateFormat.format(new Date())+"   ");

            empty = true;
            getCond.signal();

        } catch (InterruptedException e) {  System.out.println("인터럽트 감지. "+e.getMessage());
        } finally {
            lock.unlock();
        }

        return data;
    }

    @Override
    public void put(int data) {
        lock.lock();
        try{
            while(!empty){
                getCond.await();
            }

            System.out.println("데이터 생산 :"+data+". "+dateFormat.format(new Date())+"   ");

            empty = false;
            this.data = data;

            putCond.signal();

        } catch (InterruptedException e) {  System.out.println("인터럽트 감지. "+e.getMessage());
        } finally {
            lock.unlock();
        }
    }
}
