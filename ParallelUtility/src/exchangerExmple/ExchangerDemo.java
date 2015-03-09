package exchangerExmple;

import java.util.concurrent.Exchanger;

public class ExchangerDemo {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<String>();
        new StringMakerThread(exchanger);
        new StringUserThread(exchanger);
    }
}
