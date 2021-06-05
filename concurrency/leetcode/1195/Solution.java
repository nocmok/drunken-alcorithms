import java.util.function.IntConsumer;

class FizzBuzz {
    private int n;

    private int counter;

    private Object lock;

    public FizzBuzz(int n) {
        this.n = n;
        this.counter = 1;
        this.lock = new Object();
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        synchronized (lock) {
            while (counter <= n) {
                while ((counter % 3 != 0) || (counter % 5 == 0)) {
                    lock.wait();
                    if (counter > n) {
                        return;
                    }
                }
                printFizz.run();
                counter += 1;
                lock.notifyAll();
            }
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        synchronized (lock) {
            while (counter <= n) {
                while ((counter % 5 != 0) || (counter % 3 == 0)) {
                    lock.wait();
                    if (counter > n) {
                        return;
                    }
                }
                printBuzz.run();
                counter += 1;
                lock.notifyAll();
            }
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        synchronized (lock) {
            while (counter <= n) {
                while ((counter % 3 != 0) || (counter % 5 != 0)) {
                    lock.wait();
                    if (counter > n) {
                        return;
                    }
                }
                printFizzBuzz.run();
                counter += 1;
                lock.notifyAll();
            }
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        synchronized (lock) {
            while (counter <= n) {
                while ((counter % 3 == 0) || (counter % 5 == 0)) {
                    lock.wait();
                    if (counter > n) {
                        return;
                    }
                }
                printNumber.accept(counter);
                counter += 1;
                lock.notifyAll();
            }
        }
    }
}