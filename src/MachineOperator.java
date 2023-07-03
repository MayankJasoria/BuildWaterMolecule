import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MachineOperator {
    public static void main(String[] args) throws InterruptedException {
        // 5 to 15 oxygen atoms
        int numOxygen = ThreadLocalRandom.current().nextInt(5, 16);
        int numHydrogen = numOxygen * 2; // ensuring there are exactly double the hydrogen atoms to allow termination.

        System.out.println("Adding " + numOxygen + " oxygen atoms and " + numHydrogen + " hydrogen atoms");

        H2OMachine machine = new H2OMachine();
        List<Thread> list = new ArrayList<>(numOxygen + numHydrogen);
        for (int i = 1; i <= numOxygen; ++i) {
            Thread thread = new Thread(() -> {
                try {
                    machine.oxygenAtom();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            list.add(thread);
            thread.setName("Oxygen atom " + i);
        }

        for (int i = 1; i <= numHydrogen; ++i) {
            Thread thread = new Thread(() -> {
                try {
                    machine.hydrogenAtom();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            list.add(thread);
            thread.setName("Hydrogen atom " + i);
        }

        Collections.shuffle(list);
        for (Thread t : list) {
            t.start();
        }

        for (Thread t : list) {
            t.join();
        }

        System.out.println("Total water molecules created: " + machine.getNumMolecules());
    }
}
