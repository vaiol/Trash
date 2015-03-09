package phaserExample;

import java.util.concurrent.Phaser;

public class SomeThread implements Runnable{

    private Phaser phaser;
    private String name;

    public SomeThread(Phaser phaser, String name) {
        this.phaser = phaser;
        this.name = name;
        phaser.register();
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (!phaser.isTerminated()) {
            System.out.println("Thread " + name + " beginning phase " + phaser.getPhase());
            phaser.arriveAndAwaitAdvance();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
