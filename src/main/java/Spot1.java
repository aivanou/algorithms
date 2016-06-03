import java.util.*;

public class Spot1 {

    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        long num = s.nextLong();
        //        long num = 1;
        int bits = nbits(num);
        for (int i = 0; i < bits / 2; ++i) {
            num = swapBits(num, i, bits - i - 1);
        }
        System.out.println(num);
    }

    private static int nbits(long number) {
        if (number == 0)
            return 0;
        return 1 + nbits(number >> 1);
    }

    private static long swapBits(long number, int ind1, int ind2) {
        int fbit = (number & (1 << ind1)) == 0L ? 0 : 1;
        int sbit = (number & (1 << ind2)) == 0L ? 0 : 1;
        number &= ~(1 << ind1);
        number &= ~(1 << ind2);
        if (fbit == 1) {
            number |= (1 << ind2);
        }
        if (sbit == 1) {
            number |= (1 << ind1);
        }
        return number;
    }
}
