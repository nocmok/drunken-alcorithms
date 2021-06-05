public class Test {

    public static void main(String[] args) {
        Runnable foo = () -> System.out.println("foo");
        Runnable bar = () -> System.out.println("bar");

        FooBar fooBar = new FooBar(10);

        Thread tFoo = new Thread(() -> {
            try {
                fooBar.foo(foo);
            } catch (InterruptedException ignore) {
            }
        });
        Thread tBar = new Thread(() -> {
            try {
                fooBar.bar(bar);
            } catch (InterruptedException ignore) {
            }
        });

        tFoo.start();
        tBar.start();

        try {
            tFoo.join();
            tBar.join();
        } catch (InterruptedException ignore) {

        }
    }
}

class FooBar {
    private int n;

    private volatile boolean order = false;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            while (order) {
                Thread.yield();
            }
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            order = true;
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            while (!order) {
                Thread.yield();
            }
            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
            order = false;
        }
    }
}