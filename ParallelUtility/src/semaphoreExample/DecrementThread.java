package semaphoreExample;

import java.util.concurrent.Semaphore;

public class DecrementThread implements Runnable{
    private Semaphore semaphore;
    private String name;

    public DecrementThread(Semaphore semaphore, String name) {
        this.semaphore = semaphore;
        this.name = name;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " ожидает разрешения");
            semaphore.acquire();
            System.out.println(name + " получил разрешение");
            for (int i = 0; i < 10; i++) {
                System.out.println(name + ", resource: " + Resources.resource);
                Resources.resource--;
            }
            System.out.println(name + ", resource: " + Resources.resource);
            semaphore.release();
            System.out.println(name + " освобождает разрешение");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

