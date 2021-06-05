import java.util.concurrent.Semaphore;

class Foo {

    private Semaphore sem = new Semaphore(1);

    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
        sem.acquire(1);
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