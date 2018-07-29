package thread.items;

public class ConcreteInterface2 implements Runnable {
    private String name;

    public ConcreteInterface2(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for(int i=0;i<10;i++)
            System.out.println(name + i + " ");
    }
}
