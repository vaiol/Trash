package cyclicBarrierExample;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String [] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new LastThread());
        System.out.println("Start!");
        new SomeThread(cyclicBarrier, "A");
        new SomeThread(cyclicBarrier, "B");
        new SomeThread(cyclicBarrier, "C");
    }
}
