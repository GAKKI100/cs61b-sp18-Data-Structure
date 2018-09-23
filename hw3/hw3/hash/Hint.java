package hw3.hash;

public class Hint {
    public static void main(String[] args) {
        System.out.println("The powers of 256 in Java are:");
        int total = 1;
        while(true) {
            System.out.println(total);
            total = ((total * 256) & 0x7FFFFFFF) ;
            total = total + 2;
        }
    }
} 
