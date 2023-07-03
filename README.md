# Problem Statement
Suppose we have a machine that creates molecules by combining atoms. We are creating water molecules by joining one 
Oxygen atom and two Hydrogen atoms. The atoms are represented by threads. The machine will wait for the required atoms
(threads) then group one Oxygen and two Hydrogen threads to simulate the creation of a molecule. The molecule then 
exits the machine. You have to ensure that one molecule is completed before moving onto the next molecule. If more than 
the required number of threads arrive, they will have to wait. The threads can arrive in any order, which means HHO, 
HOH, OHH are all valid outputs. The code for the class is as follows:
```java
public class H2OMachine {
    
    public void hydrogenAtom() {
        
    }
    
    public void oxygenAtom() {
        
    }
}
```
The input to the machine can be in any order. Your program should enforce a 2:1 ratio for Hydrogen and Oxygen threads, 
and stop more than the required number of threads from entering the machine.

## Solution Approach
1. Implemented an unbounded semaphore and a cyclic barrier which resets after releasing fixed number of awaiting 
threads but allows any additional incoming threads to wait for reset. The barrier invokes an optional callback with any 
one of the released threads. 
2. The class uses two semaphores, `oxygenSemaphore` with 1 initial permit and `hydrogenSemaphore` with 2 initial 
permits, and one instance of the cyclic barrier which breaks with 3 threads and invokes a callback once broken.
3. `hydrogenAtom()` acquires the `hydrogenSemaphore` once, adds the hydrogen atom, then waits on the barrier.
4. `oxygenArom()` acquires the `oxygenSemaphore` once, adds the oxygen atom, them waits on for the barrier.
5. The barrier uses a callback function which creates the water molecule, increases the count of number of molecules
created, then releases the `oxygenSemaphore` once and `hydrogenSemaphore` twice.

## Sample Output
```
Adding 12 oxygen atoms and 24 hydrogen atoms
Added hydrogen atom: Hydrogen atom 2
Added hydrogen atom: Hydrogen atom 17
Added oxygen atom: Oxygen atom 11
Created water molecule
Added oxygen atom: Oxygen atom 8
Added hydrogen atom: Hydrogen atom 8
Added hydrogen atom: Hydrogen atom 21
Created water molecule
Added oxygen atom: Oxygen atom 5
Added hydrogen atom: Hydrogen atom 20
Added hydrogen atom: Hydrogen atom 13
Created water molecule
Added oxygen atom: Oxygen atom 1
Added hydrogen atom: Hydrogen atom 24
Added hydrogen atom: Hydrogen atom 14
Created water molecule
Added oxygen atom: Oxygen atom 4
Added hydrogen atom: Hydrogen atom 6
Added hydrogen atom: Hydrogen atom 7
Created water molecule
Added oxygen atom: Oxygen atom 7
Added hydrogen atom: Hydrogen atom 15
Added hydrogen atom: Hydrogen atom 18
Created water molecule
Added hydrogen atom: Hydrogen atom 10
Added oxygen atom: Oxygen atom 9
Added hydrogen atom: Hydrogen atom 4
Created water molecule
Added oxygen atom: Oxygen atom 3
Added hydrogen atom: Hydrogen atom 23
Added hydrogen atom: Hydrogen atom 3
Created water molecule
Added oxygen atom: Oxygen atom 2
Added hydrogen atom: Hydrogen atom 11
Added hydrogen atom: Hydrogen atom 12
Created water molecule
Added oxygen atom: Oxygen atom 12
Added hydrogen atom: Hydrogen atom 16
Added hydrogen atom: Hydrogen atom 5
Created water molecule
Added oxygen atom: Oxygen atom 6
Added hydrogen atom: Hydrogen atom 22
Added hydrogen atom: Hydrogen atom 9
Created water molecule
Added hydrogen atom: Hydrogen atom 1
Added hydrogen atom: Hydrogen atom 19
Added oxygen atom: Oxygen atom 10
Created water molecule
Total water molecules created: 12
```