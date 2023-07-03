public class Semaphore {

    private int permit;

    public Semaphore(int initialPermit) {
        this.permit = initialPermit;
    }

    public synchronized void acquire() throws InterruptedException {
        while (permit == 0) {
            wait();
        }

        --permit;
    }

    public synchronized void release () {
        ++permit;
        notify();
    }
}
