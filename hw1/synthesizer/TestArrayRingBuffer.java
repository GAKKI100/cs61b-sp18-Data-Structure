package synthesizer;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    //@Test
    public static void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(10);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        arb.dequeue();
        arb.dequeue();
        Iterator<Integer> am = arb.iterator();
        while (am.hasNext()){
            for (Integer y : arb){
                System.out.println("x: " + am.next() +  ", y:" + y);
            }
        }
    }


    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        //jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
        someTest();
    }
} 
