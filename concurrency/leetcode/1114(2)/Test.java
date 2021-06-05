import java.util.List;
import java.util.concurrent.Semaphore;

public class Test {

    interface Callback<T> {
        public void accept(T value) throws InterruptedException;
    }

    public static void main(String[] args) {

        // sem initially has 2 permits
        // t1 acquire 3 permits
        // t2 acquire 2 permits and release 3

        Semaphore sem = new Semaphore(0, false);
        Thread t1 = new Thread(() -> {
            try {
                sem.acquire(3);
                System.out.println("t1 unblocked");
            } catch (InterruptedException ignore) {
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                sem.acquire(2);
                System.out.println("t2 unblocked");
            } catch (InterruptedException ignore) {
            }
        });

        try {
            t1.start();
            t2.start();

            Thread.sleep(1000);
            sem.release(2);
            sem.release(3);

            t2.join();
            t1.join();
        } catch (InterruptedException ignore) {

        }

        // Foo foo = new Foo();
        // String[] messages = new String[] { "first", "second", "third" };
        // Thread[] threads = new Thread[3];
        // List<Callback<Runnable>> callbacks = List.of(foo::first, foo::second,
        // foo::third);

        // for (int i = 0; i < 3; ++i) {
        // String msg = messages[i];
        // Callback<Runnable> callback = callbacks.get(i);

        // threads[i] = new Thread(() -> {
        // try {
        // callback.accept(() -> System.out.println(msg));
        // } catch (InterruptedException ignore) {
        // }
        // });
        // }
        // for (int i = 0; i < 3; ++i) {
        // threads[i].start();
        // }
        // for (int i = 0; i < 3; ++i) {
        // try {
        // threads[i].join();
        // } catch (InterruptedException ignore) {
        // }
        // }
    }
}

class Foo {

    private Semaphore sem = new Semaphore(0, false);

    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        sem.release(2);

    }

    public void second(Runnable printSecond) throws InterruptedException {
        sem.acquire(2);
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        sem.release(3);

    }

    public void third(Runnable printThird) throws InterruptedException {
        sem.acquire(3);
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}