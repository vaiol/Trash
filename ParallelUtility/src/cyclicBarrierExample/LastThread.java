package cyclicBarrierExample;

public class LastThread implements Runnable {
    @Override
    public void run() {
        System.out.println("Barrier broken!");
    }
}
