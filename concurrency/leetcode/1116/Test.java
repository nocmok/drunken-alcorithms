import java.util.function.IntConsumer;

public class Test {

    public static void main(String[] args) {
        int n = 100;
        ZeroEvenOdd zeo = new ZeroEvenOdd(n);

        Thread a = new Thread(() -> {
            try {
                zeo.zero((v) -> System.out.println(v));
            } catch (InterruptedException ignore) {
            }
        });
        Thread b = new Thread(() -> {
            try {
                zeo.even((v) -> System.out.println(v));
            } catch (InterruptedException ignore) {
            }
        });
        Thread c = new Thread(() -> {
            try {
                zeo.odd((v) -> System.out.println(v));
            } catch (InterruptedException ignore) {
            }
        });

        a.start();
        b.start();
        c.start();

        try {
            a.join();
            b.join();
            c.join();
        } catch (InterruptedException ignore) {

        }
    }
}

class ZeroEvenOdd {
    private int n;

    private int order;

    private static final int ZERO = 1;

    private static final int EVEN = 2;

    private static final int ODD = 3;

    private int rest;

    private Object lock;

    public ZeroEvenOdd(int n) {
        this.n = n;
        this.order = ZERO;
        this.rest = 2 * n;
        this.lock = new Object();
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        synchronized (lock) {
            int nextValue = 0;
            int next = ODD;

            while (rest > 0) {
                while (order != ZERO) {
                    lock.wait();
                    if (rest == 0) {
                        return;
                    }
                }
                printNumber.accept(nextValue);
                rest -= 1;
                order = next;
                next = ((next == ODD) ? EVEN : ODD);
                lock.notifyAll();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        synchronized (lock) {
            int nextValue = 2;
            while (rest > 0) {
                while (order != EVEN) {
                    lock.wait();
                    if (rest == 0) {
                        return;
                    }
                }
                printNumber.accept(nextValue);
                nextValue += 2;
                rest -= 1;
                order = ZERO;
                lock.notifyAll();
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        synchronized (lock) {
            int nextValue = 1;
            while (rest > 0) {
                while (order != ODD) {
                    lock.wait();
                    if (rest == 0) {
                        return;
                    }
                }
                printNumber.accept(nextValue);
                nextValue += 2;
                rest -= 1;
                order = ZERO;
                lock.notifyAll();
            }
        }
    }
}