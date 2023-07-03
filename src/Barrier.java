import java.util.Objects;

public class Barrier {

    private final Runnable callback;
    private final int numThreads;
    private int count = 0;
    private int released = 0;

    public Barrier(int numThreads) {
        callback = null;
        this.numThreads = numThreads;
    }

    public Barrier(int numThreads, Runnable callback) {
        this.callback = callback;
        this.numThreads = numThreads;
    }

    public synchronized void await() throws InterruptedException {
        while (count == numThreads) {
            wait();
        }

        ++count;
        while (count < numThreads) {
            wait();
        }

        if (released == 0) {
            notifyAll();
        }
        ++released;

        if (Objects.nonNull(callback) && released == 1) {
            // allow only one thread to run the callback
            callback.run();
        }

        if (released == count) {
            // will only be executed by the last thread leaving the barrier
            count = 0;
            released = 0;
            notifyAll();
        }
    }
}
