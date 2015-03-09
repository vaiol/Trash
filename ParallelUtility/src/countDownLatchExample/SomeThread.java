package countDownLatchExample;

import java.util.concurrent.CountDownLatch;

public class SomeThread implements Runnable {

    private CountDownLatch latch;

    public SomeThread(CountDownLatch latch) {
        this.latch = latch;
        new Thread(this).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
            latch.countDown();
        }
    }
}
