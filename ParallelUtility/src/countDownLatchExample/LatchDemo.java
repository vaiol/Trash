package countDownLatchExample;

import java.util.concurrent.CountDownLatch;

public class LatchDemo {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(10);
        System.out.println("START");
        new SomeThread(latch);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("END");
    }
}
