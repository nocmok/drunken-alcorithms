import java.util.List;

public class Test {

    interface Callback<T> {
        public void accept(T value) throws InterruptedException;
    }

    public static void main(String[] args) {
        Foo foo = new Foo();
        String[] messages = new String[] { "first", "second", "third" };
        Thread[] threads = new Thread[3];
        List<Callback<Runnable>> callbacks = List.of(foo::first, foo::second, foo::third);

        for (int i = 0; i < 3; ++i) {
            String msg = messages[i];
            Callback<Runnable> callback = callbacks.get(i);

            threads[i] = new Thread(() -> {
                try {
                    callback.accept(() -> System.out.println(msg));
                } catch (InterruptedException ignore) {
                }
            });
        }
        for (int i = 0; i < 3; ++i) {
            threads[i].start();
        }
        for (int i = 0; i < 3; ++i) {
            try {
                threads[i].join();
            } catch (InterruptedException ignore) {
            }
        }
    }
}

class Foo {

    private volatile int order = 1;

    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
        while (order != 1) {
        }
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        order = 2;
    }

    public void second(Runnable printSecond) throws InterruptedException {
        while (order != 2) {
        }
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        order = 3;
    }

    public void third(Runnable printThird) throws InterruptedException {
        while (order != 3) {
        }
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
        order = 4;
    }
}