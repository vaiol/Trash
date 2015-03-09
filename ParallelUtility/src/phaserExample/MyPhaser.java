package phaserExample;

import java.util.concurrent.Phaser;

/**
 * Расширение класса Phaser, чтобы выполнять определенное количество фаз.
 */
public class MyPhaser extends Phaser {
    private int countOfPhas;

    public MyPhaser(int parties, int countOfPhas) {
        super(parties);
        this.countOfPhas = countOfPhas;
    }

    protected boolean onAdvance(int p, int regParties) {
        System.out.println("Phase " + p + "completed");
        return (p == countOfPhas || regParties == 0);
    }
}
