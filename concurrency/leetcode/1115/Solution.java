class FooBar {
    private int n;

    private volatile boolean order = false;

    private Object lock = new Object();

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            synchronized (lock) {
                while (order) {
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                order = true;
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            synchronized (lock) {
                while (!order) {
                }
                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                order = false;
            }
        }
    }
}