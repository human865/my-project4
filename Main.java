import java.util.Scanner;

public class Main {

    public static double f(double x) {
        return 2 * x - 1.0 / Math.tan(x);
    }

    public static double df(double x) {
        double s = Math.sin(x);
        return 2 + 1.0 / (s * s);
    }

    public static double newton(double x0, double eps, int maxIter) {
        double x = x0;
        for (int i = 0; i < maxIter; i++) {
            double fx = f(x);
            double dfx = df(x);

            if (Math.abs(dfx) < 1e-12) {
                System.out.println("Производная близка к нулю, метод не работает.");
                return Double.NaN;
            }

            double xNext = x - fx / dfx;

            System.out.printf("Итерация %d: x = %.8f, f(x) = %.8f%n", i + 1, xNext, f(xNext));

            if (Math.abs(xNext - x) < eps) {
                return xNext;
            }
            x = xNext;
        }
        System.out.println("Достигнуто максимальное число итераций.");
        return x;
    }

    public static double chord(double a, double b, double eps, int maxIter) {
        double x;
        double fa = f(a);
        double fb = f(b);

        if (fa * fb > 0) {
            System.out.println("На концах отрезка функция имеет одинаковые знаки!");
            return Double.NaN;
        }

        for (int i = 0; i < maxIter; i++) {
            x = a - fa * (b - a) / (fb - fa);
            double fx = f(x);

            System.out.printf("Итерация %d: x = %.8f, f(x) = %.8f%n", i + 1, x, fx);

            if (Math.abs(fx) < eps) {
                return x;
            }

            if (fa * fx < 0) {
                b = x;
                fb = fx;
            } else {
                a = x;
                fa = fx;
            }
        }
        System.out.println("Достигнуто максимальное число итераций.");
        return a;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Введите левую границу a: ");
        double a = sc.nextDouble();

        System.out.print("Введите правую границу b: ");
        double b = sc.nextDouble();

        System.out.print("Введите точность eps: ");
        double eps = sc.nextDouble();

        System.out.print("Введите начальное приближение x0 (для метода Ньютона): ");
        double x0 = sc.nextDouble();

        System.out.print("Введите максимальное число итераций n: ");
        int n = sc.nextInt();

        System.out.println("\nВыберите метод:");
        System.out.println("1 — Метод Ньютона");
        System.out.println("2 — Метод Хорд");
        System.out.print("Ваш выбор: ");
        int choice = sc.nextInt();

        System.out.println("\n=== Процесс вычисления ===");

        double root;
        if (choice == 1) {
            root = newton(x0, eps, n);
        } else {
            root = chord(a, b, eps, n);
        }

        System.out.println("\n=== Результат ===");
        if (Double.isNaN(root)) {
            System.out.println("Корень не найден.");
        } else {
            System.out.printf("Приближённый корень: x ≈ %.6f%n", root);
            System.out.printf("Значение f(x) = %.10f%n", f(root));
        }

        sc.close();
    }
}