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