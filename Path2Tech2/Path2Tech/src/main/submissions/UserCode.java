import java.util.*;

public class UserCode {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] numbers = sc.nextLine().split(" ");
        int max = Integer.MIN_VALUE;

        for (String numStr : numbers) {
            int num = Integer.parseInt(numStr);
            if (num > max) {
                max = num;
            }
        }

        System.out.println(max);
    }
}
