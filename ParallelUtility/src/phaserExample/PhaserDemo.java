package phaserExample;

public class PhaserDemo {
    public static void main(String [] args) {
        MyPhaser phaser = new MyPhaser(1, 4);
        System.out.println("Starting!");

        new SomeThread(phaser, "A");
        new SomeThread(phaser, "B");
        new SomeThread(phaser, "C");

        while(!phaser.isTerminated()) {
            phaser.arriveAndAwaitAdvance();
        }
        System.out.println("The phaser is terminated!");
    }
}
