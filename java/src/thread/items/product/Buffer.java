package thread.items.product;

public class Buffer {

    private int data;
    private boolean empty = true;

    public synchronized int get(){
        while (empty){
            try {
                wait(); //생산될 때 까지 기다린다.
            } catch (InterruptedException e) {  e.printStackTrace(); }
        }

        empty = true;
        notifyAll();

        return data;
    }

    public synchronized void put(int data){
        while (!empty){
            try {
                wait(); //소비 될 때까지 기다린다.
            } catch (InterruptedException e) { e.printStackTrace(); }
        }

        empty = false;
        this.data = data;

        notifyAll();
    }
}
