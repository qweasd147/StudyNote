package thread.items;

public class CheckCallStack implements Runnable{

    @Override
    public void run() {
        throwException();
    }

    private void throwException() {
        try {
            throw new Exception("Exception");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
