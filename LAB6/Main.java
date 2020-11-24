package com.company;

import static java.lang.StrictMath.*;

class BealeFunction implements Function {
    public double calculate(double x, double y) {
        double z = pow((1.5 - x + (x * y)), 2) + pow((2.25 - x + (x * pow(y, 2))), 2) + pow((2.626 - x + (x * pow(y, 3))), 2);
        //System.out.println(z);
        return z;
    }

    @Override
    public String name() {
        return "Beale";
    }
}

class MatyasFunction implements Function {
    public double calculate(double x, double y) {
        double z= 0.26 * (pow(x, 2) + pow(y, 2)) - 0.48 * x * y;
        //System.out.println(z);
        return z;
    }

    @Override
    public String name() {
        return "Matyas";
    }
}

class BoothFunction implements Function {
    public double calculate(double x, double y) {
        double z= pow(x + 2 * y - 7, 2) + pow(2 * x + y - 5, 2);
        //System.out.println(z);
        return z;
    }

    @Override
    public String name() {
        return "Booth";
    }
}

public class Main {
    public static void randomSampling(int n, double min, double max, Function function) {
        double best = 10, bestx = 0, besty = 0, temp = 0;
        System.out.println(function.name() + " Function");

        for (int i = 0; i < n; i++) {
            double x = Math.random() * (max - min + 1) + min;
            double y = Math.random() * (max - min + 1) + min;
            temp = function.calculate(x, y);
            if (temp == 0) {
                bestx = x;
                besty = y;
                break;
            } else if (abs(temp) <= best) {
                best = temp;
                bestx = x;
                besty = y;
            }
        }
        System.out.println("najlepszy wynik dla x:" + bestx + " i y:" + besty + " roznica od 0: " + best + "\n");
    }

    public static void main(String[] args) {
        /*
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wybierz funkcje testowa od 1 do 3");
        int x = scanner.nextInt();
        System.out.println("Wybierz max zakres");
        double max = scanner.nextDouble();
        System.out.println("Wybierz min zakres");
        double min = scanner.nextDouble();
        System.out.println("Wybierz ilosc iteracji");
        int n = scanner.nextInt();

        randomSampling(x, n, min, max);
        */

        // 1 - beale - -4.5 do 4.5
        // 2 - matyas - -10 do -10
        // 3 - booth - -10 do 10
        //ile iteracji - n

        BealeFunction bealeFunction = new BealeFunction();
        MatyasFunction matyasFunction = new MatyasFunction();
        BoothFunction boothFunction = new BoothFunction();

        randomSampling(1000, -4.5, 4.5, bealeFunction);
        randomSampling(1000, -10, 10, matyasFunction);
        randomSampling(1000, -10, 10, boothFunction);
    }
}