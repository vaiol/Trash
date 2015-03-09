package exchangerExmple;

import java.util.concurrent.Exchanger;

public class StringUserThread implements Runnable{

    private Exchanger<String> exchanger;
    private String string;
    private int countOfExchange;

    public StringUserThread(Exchanger<String> exchanger, int countOfExchange) {
        this.exchanger = exchanger;
        string = "";
        this.countOfExchange = countOfExchange;
        new Thread(this).start();
    }

    public StringUserThread(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
        string = "";
        countOfExchange = 5;
        new Thread(this).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < countOfExchange; i++) {
            try {
                string = exchanger.exchange("");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" - Получено: " + string);
        }
    }
}
