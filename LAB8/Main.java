package com.company.LAB8;

import java.util.Arrays;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.pow;

/*
Zaimplementuj zadanie optymalizacyjne tak, aby można było je liczyć za pomocą algorytmu genetycznego. Teraz bardziej szczegółowo:

Wybierz jedno z zadań optymalizacyjnych, ale tylko takie które ma wiele minimów lokalnych.
Na potrzeby ćwiczeń zmienne z dziedziny funkcji nazwę X oraz Y, ale oczywiście jeśli wybrana została przez Ciebie funkcja
która przyjmuje więcej argumentów, to oczywiście będzie tu X0, X1, .. Xn

------------------------------------------------------------------------------------------------------------------------

Przygotuj funkcję która przekształci tablicę 128 liczb ze zbioru {0,1} na odpowiednio X oraz Y.
Na dodatkowy punkt - niech będzie to liczba w kodzie Greay-a (https://pl.wikipedia.org/wiki/Kod_Graya).
To będzie funkcja dekodująca genotyp do postaci fenotypu (czyli tak jak to na wykładzie było - ciąg kodowy w konkretne rozwiązanie). Pamiętaj o ułamkach!

^DONE^

Napisz funkcję oceny (inaczej funkcję fitness), która będzie oceniała fenotyp. W tym celu należy zrobić funkcję,
która będzie na przykład odwracała funkcję celu (funkcje testowe do optymalizacji są minimalizowane, a funkcja oceny jest maksymalizowana,
więc trzeba przekształcić jedną w drugą). Przy tym punkcie zachęcam do zadawania pytań.

^DONE^

Przetestuj ręcznie, czy faktycznie to działa. Dodaj możliwość konwersji genotypu w fenotyp oraz fenotypu w genotyp.
(czyli tablica 128 liczb na X,Y oraz z X,Y na tablicę 128 liczb).

^DONE^

Przetestuj, czy algorytm losowego próbkowania da radę tu wygenerować jakieś sensowne rozwiązanie. - to na dodatkowe 0.5pkt

^DONE^
 */

public class Main {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final int size = 40;
    public static final int accuracy = 5;

    static double bealeFunction(double x, double y) {
        return pow((1.5 - x + (x * y)), 2) + pow((2.25 - x + (x * pow(y, 2))), 2) + pow((2.626 - x + (x * pow(y, 3))), 2);
    }

    static int[] toBinary(double x){
        int result = (int) (x*pow(10,accuracy));
        int[] tab = new int[size/2];
        for(int i =0;i<size/2;i++){
            tab[i]=result%2;
            result=result/2;
        }
        reverseArr(tab);
        return tab;

    }
    static void reverseArr(int[] tab){
        for(int i = 0; i < tab.length / 2; i++) {
            int temp = tab[i];
            tab[i] = tab[tab.length - i - 1];
            tab[tab.length - i - 1] = temp;
        }
    }
    static double toDecimal(int[] tab){
        double result =0;
        reverseArr(tab);
        for (int i=0;i<size/2;i++){
            result+=tab[i]*pow(2,i);
        }
        return result;
    }

    static void generate(int[] tab){
        for (int i=0;i<size;i++){
            if(Math.random()<0.5) tab[i]=0;
            else tab[i]=1;
        }
    }

    static void toGray(int[] tab){
        for(int i=1;i<size;i++){
            if(tab[i]!=tab[i-1]) tab[i]=1;
            else tab[i]=0;
        }
    }

    static void toPhenotype(int[] tab, double[] result){
        int[] tempX = new int[size/2];
        int[] tempY = new int[size/2];
        for(int i=0;i<size/2;i++){
            tempX[i]=tab[i];
            tempY[i]=tab[i+size/2];
        }
        result[0] = toDecimal(tempX)/pow(10,accuracy);
        result[1] = toDecimal(tempY)/pow(10,accuracy);
    }

    static void toGenotype(int[] tab, double x, double y){
        int[] tempX = toBinary(x);
        int[] tempY = toBinary(y);
        for(int i=0;i<size/2;i++){
            tab[i]=tempX[i];
            tab[i+size/2]=tempY[i];
        }
    }

    static double fitness(double[]fen, double aim){
        return 1/abs(aim - bealeFunction(fen[0],fen[1]));
    }

    static void randomSampling(int iterations,  double aim){
        int[] tab = new int[size];
        generate(tab);

        double[] fen = new double[2];
        toPhenotype(tab,fen);

        double fitness = fitness(fen,aim);

        for (int i=0;i<iterations;i++){
            int[] tempTab = new int[size];
            generate(tempTab);
            toPhenotype(tempTab,fen);
            double tempFitness = fitness(fen,aim);
            if(fitness<tempFitness){
                fitness = tempFitness;
            }
        }
        System.out.println(ANSI_GREEN+"Best Fitness:\n"+ANSI_RESET+ fitness);
    }



    public static void main(String[] args) {

        int[] tab = new int[size];
        int[] fenToGen = new int[size];
        double[] fen = new double[2];

        generate(tab);
        System.out.println(ANSI_GREEN+"Randomly Generated Array:\n"+ANSI_RESET+Arrays.toString(tab));

        toGray(tab);
        System.out.println(ANSI_GREEN+"Array In Gray Code:\n"+ANSI_RESET+Arrays.toString(tab));

        toPhenotype(tab,fen);
        System.out.println(ANSI_GREEN+"Phenotype:\n"+ANSI_RESET+fen[0]+" , "+fen[1]);

        toGenotype(fenToGen,fen[0],fen[1]);
        System.out.println(ANSI_GREEN+"Phenotype Converted to Genotype:\n"+ANSI_RESET+Arrays.toString(fenToGen));

        randomSampling(100000,0);
    }
}
