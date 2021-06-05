import java.util.concurrent.Semaphore;

class H2O {

    private final Object lock;

    private Semaphore oSem;

    private Semaphore hSem;

    private Semaphore sem;

    public H2O() {
        lock = new Object();
        oSem = new Semaphore(0);
        hSem = new Semaphore(0);
        sem = new Semaphore(0);
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        sem.release(1);
        oSem.acquire(1);

        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();

        hSem.release(1);
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        synchronized (lock) {
            sem.acquire(2);
            oSem.release(2);

            // releaseOxygen.run() outputs "O". Do not change or remove this line.
            releaseOxygen.run();

            hSem.acquire(2);
        }
    }
}