import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Spot1 {
    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        int t = s.nextInt();
        s.nextLine();
        for (int i = 1; i <= t; ++i) {
            String[] str = s.nextLine().split(" ");
            System.out.print("Case #" + i + ": ");
            for (int j = str.length - 1; j >= 0; --j)
                System.out.print(str[j] + " ");
            System.out.println();
        }
    }
}
