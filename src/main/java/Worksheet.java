import java.util.Scanner;

public class Worksheet {

    public static void main(String[] args) {
        long st = System.nanoTime();
        int[] i=new int[10000000];
        System.out.println((System.nanoTime()-st)/1000000000.0);
    }

}
