package thread.items;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedResourceWithLocks {

    private final Lock lock = new ReentrantLock();
    private final Condition isEmpty = lock.newCondition();
    private final Condition isFull = lock.newCondition();

    private volatile boolean hasData = false;

    public void consum(){

        //TODO : 락이 필요 없는 부분들

        //락이 필요한 부분들
        lock.lock();
        try{
            while(hasData){

            }

        }finally {
            //락이 안풀릴 수도 있으므로 final에서 unlock
            lock.unlock();
        }


    }
}
