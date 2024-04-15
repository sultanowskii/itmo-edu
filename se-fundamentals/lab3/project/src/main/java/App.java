public class App {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: <app> a b");
            return;
        }

        int a, b;
        try {
            b = Integer.parseInt(args[1]);
            a = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("a and b must be integers");
            return;
        }

        int sum = Calculator.sum(a, b);
        int mul = Calculator.mul(a, b);

        System.out.printf("a + b = %d\n", sum);
        System.out.printf("a * b = %d\n", mul);
    }
}
