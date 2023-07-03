public class H2OMachine {

    private final Semaphore oxygenSemaphore;
    private final Semaphore hydrogenSemaphore;
    private final Barrier moleculeFormationBarrier;
    private int numMolecules;

    public H2OMachine() {
        this.oxygenSemaphore = new Semaphore(1);
        this.hydrogenSemaphore = new Semaphore(2);
        this.moleculeFormationBarrier = new Barrier(3, () -> {
            System.out.println("Created water molecule");
            ++numMolecules;
            oxygenSemaphore.release();
            hydrogenSemaphore.release();
            hydrogenSemaphore.release();
        });
        this.numMolecules = 0;
    }

    public void hydrogenAtom() throws InterruptedException {
        hydrogenSemaphore.acquire();
        System.out.println("Added hydrogen atom: " + Thread.currentThread().getName());
        moleculeFormationBarrier.await();
    }

    public void oxygenAtom() throws InterruptedException {
        oxygenSemaphore.acquire();
        System.out.println("Added oxygen atom: " + Thread.currentThread().getName());
        moleculeFormationBarrier.await();
    }

    public int getNumMolecules() {
        return this.numMolecules;
    }
}
