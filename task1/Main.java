import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int currentTask;
        int iterations = 0;
        double x = 0.625 , a = 0, b = 2;
        final double epsTwo = Math.pow(10, -8);
        final double epsOne = Math.pow(10, -4);
        String subTask;
        Scanner in = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        System.out.println("Выберите задание: 1/2");
        currentTask = in.nextInt();
        if (currentTask == 1) {
            System.out.println("Выберите номер: a/b/c/d/e/f");

            subTask = input.nextLine();

            switch (subTask) {
                case ("a") -> {
                    a = 0;
                    b = 2;
                    x = 0.625;
                    break;
                }
                case ("b") -> {
                    a = 1;
                    b = 2;
                    x = 1.5;
                    break;
                }
                case ("c") -> {
                    a = -1;
                    b = 0;
                    x = -0.99;
                    break;
                }
                case ("d") -> {
                    a = 0;
                    b = 2;
                    x = 1.5;
                    break;
                }
                case ("e") -> {
                    a = 1.25;
                    b = 1.5;
                    x = 1.3;
                    break;
                }
                case ("f") -> {
                    a = 0.625;
                    b = 1.25;
                    x = 0.625;
                    break;
                }

            }
            dichotomy(a, b, epsOne, 0, subTask);
            newton(x, epsOne, 0, subTask);
        }
        if (currentTask == 2) {
            System.out.println("Выберите номер: a/b");
            subTask = input.nextLine();
            switch (subTask) {
                case ("a") -> {
                    x = 3.14;
                    while (Math.abs(Math.sin(x)) > epsTwo) {
                        x = x - (Math.sin(x) / Math.cos(x));
                        iterations++;
                    }
                    System.out.println("Ответ: " + x + "\n" + "Количество итераций: " + iterations);
                }
                case ("b") -> {
                    x = 2.718;
                    while (Math.abs(Math.log(Math.abs(x)) - 1) > epsTwo) {
                        x = x - (Math.log(x) - 1) * x;
                        iterations++;
                    }
                    System.out.println("Ответ: " + x + "\n" + "Количество итераций: " + iterations);
                }
            }
        }

    }
    static double functionA(double x) { return Math.sin(x) - 2 * x * x + 0.5; }
    static double functionB(double x) {return x * x * x - 4; }
    static double functionC(double x) { return Math.sqrt(1 - x * x) - Math.exp(x) + 0.1; }
    static double functionD(double x) { return Math.pow(x, 6) - 5 * x * x * x - 2; }
    static double functionE(double x) { return Math.log(x) - 1 / (1 + x * x); }
    static double functionF(double x) { return Math.pow(3, x) - 5 * x * x + 1; }

    static double functionUltimate(double x, String subTask) {
        return switch (subTask) {
            case ("a") -> functionA(x);
            case ("b") -> functionB(x);
            case ("c") -> functionC(x);
            case ("d") -> functionD(x);
            case ("e") -> functionE(x);
            case ("f") -> functionF(x);
            default -> x;
        };
    }

    public static void dichotomy(double a, double b, double epsOne, int iterationsDih, String subTask) {
        while (b - a > epsOne) {
            double c = (a + b) / 2;
            if (functionUltimate(b, subTask) * functionUltimate(c, subTask) < 0) {
                a = c;
            } else {
                b = c;
            }
            iterationsDih++;
        }
        System.out.println(((a + b) / 2) + " Количество итераций Дихотомии: " + iterationsDih);
    }
    public static double derivative(double x, String subTask) {
        return switch (subTask) {
            case ("a") -> Math.cos(x) - 4 * x;
            case ("b") -> 3 * x * x;
            case ("c") -> -1 * ((x + Math.sqrt(1 - x * x) * Math.exp(x)) / Math.sqrt(1 - x * x));
            case ("d") -> 6 * Math.pow(x, 5) - 15 * x * x;
            case ("e") -> (1 / x) + ((2 * x) / ((1 + x * x) * (1 + x * x)));
            case ("f") -> Math.log(3) * Math.pow(3, x) - 10 * x;
            default -> x;
        };
    }
    public static void newton(double x, double epsOne, int iterationsNew, String subTask) {
        while (Math.abs(functionUltimate(x, subTask)) > epsOne) {
            x = x - (functionUltimate(x, subTask) / (derivative(x, subTask)));
            iterationsNew++;
        }
        System.out.println(x + " Количество итераций Ньютон: " + iterationsNew);
    }
}