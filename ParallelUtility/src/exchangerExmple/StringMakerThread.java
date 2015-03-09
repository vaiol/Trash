package exchangerExmple;

import java.util.concurrent.Exchanger;

public class StringMakerThread  implements Runnable {

    private Exchanger<String> exchanger;
    private String string;
    private int countOfExchange;

    public StringMakerThread(Exchanger<String> exchanger, int countOfExchange) {
        this.exchanger = exchanger;
        string = "";
        this.countOfExchange = countOfExchange;
        new Thread(this).start();
    }

    public StringMakerThread(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
        string = "";
        this.countOfExchange = 5;
        new Thread(this).start();
    }

    @Override
    public void run() {
        char ch = 'A';
        for (int i = 0; i < countOfExchange; i++) {
            //заполнение буфера
            for (int j = 0; j < 5; j++) {
                string += ch++;
            }
            try {
                System.out.println(" Отправлено: " + string);
                string = exchanger.exchange(string);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
