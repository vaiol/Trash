package semaphoreExample;

import java.util.concurrent.Semaphore;

/**
 * Semaphore один из видов синхронизации потоков. Синхронизация потоков переносится в отдельный класс - Semaphore.
 * Semaphore чаще всего применяется для синхронизации доступа к ресурсу.
 * При этом логика предоставления доступа переносится с ресурса на Semaphore.
 * Это предоставляет такие фишки как: предоставление доступа на время (например для того, чтобы
 * поток имел возможность совершить некоторое количество операций с ресурсом, прежде чем потерять доступ),
 * а также предоставление доступа сразу нескольким потокам (например, ограничивать доступ к БД).
 * //Если создать new Semaphore(0); разрешение к нему можно будет получить только после release();
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        int countOfThreads = 1;
        Semaphore semaphore = new Semaphore(countOfThreads);
        new IncrementThread(semaphore,"Inc");
        new DecrementThread(semaphore,"Dec");
    }
}
