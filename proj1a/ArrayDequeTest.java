public class ArrayDequeTest {
    public static void main(String[] args){
        ArrayDeque<Integer> a = new  ArrayDeque<Integer>();
        System.out.println(a.isEmpty());
        a.addFirst(1); //096151754
        a.addLast(5);
        a.addFirst(6);
        a.addLast(1);
        a.addLast(7);
        a.addFirst(9);
        a.addLast(5);
        a.addLast(4);
        a.addFirst(0);
        a.printDeque();
        a.get(5);
        int b, c, d, e, f, g;
        b = a.removeLast();//09615175
        c = a.removeFirst();//9615175
        d = a.removeLast();//961517
        e = a.removeLast();//96151
        f = a.removeFirst();//6151
        g = a.removeFirst();//151
        System.out.println(a.isEmpty());
        a.printDeque();
    }

}
