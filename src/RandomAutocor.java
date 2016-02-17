import java.math.BigInteger;
import java.util.Random;

import static java.lang.Math.abs;

/**
 * Created by serg3z on 17.02.16.
 */
public class RandomAutocor {

    public static final int NUMBER_COUNT = 1000000;
    public static final int INTERVAL = 100;
    public static final int SIZE = 4;

    public RandomAutocor(boolean flag, boolean showArrayFlag) {
        double[] Array = new double[NUMBER_COUNT];

        if (flag == true) {
            Random random = new Random();
            System.out.println("Standart rand");
            for (int i = 0; i < Array.length; i++) {
                Array[i] = random.nextDouble();
            }
        } else {
            System.out.println("Math rand");
            for (int i = 0; i < Array.length; i++) {
                Array[i] = Math.random();
            }
        }
        if (showArrayFlag) {
            showMas(Array);
        }
        autocorr(Array);
        xi2(Array);
    }

    private void showMas(double[] mas) {
        for (int i = 0; i < mas.length; i++) {
            System.out.println(i + " - " + mas[i]);
        }
    }

    private void autocorr(double[] mas) {
        double Dx, Mx;
        double sumMx = 0.0, sumDx = 0.0;
        double cor;

        for (int j = 0; j < NUMBER_COUNT; ++j) {
            sumMx += mas[j];
            sumDx += mas[j] * mas[j];
        }
        Mx = (sumMx / NUMBER_COUNT) * (sumMx / NUMBER_COUNT);
        Dx = (sumDx / NUMBER_COUNT) - Mx;

        for (int t = 1; t < SIZE; t += 1) {
            cor = 0.0;
            for (int i = 0; i < NUMBER_COUNT - t; ++i) {
                cor += mas[i] * mas[i + t];
            }
            cor /= NUMBER_COUNT - t;
            cor -= Mx;
            cor /= (Dx - Mx);
            System.out.printf("cor - %f\n", abs(cor));
        }
    }

    private static void xi2(double[] mas) {
        BigInteger V = new BigInteger(String.valueOf(512));

        int[] Y = new int[INTERVAL];

        double interval = 1.0 / INTERVAL;

        for (int i = 0; i < NUMBER_COUNT; ++i) {
            Y[(int) (mas[i] / interval)]++;
        }
        for (int i = 0; i < INTERVAL; ++i) {
            V = V.add(BigInteger.valueOf((long) (Y[i] * Y[i] / interval)));
        }
        V = V.divide(BigInteger.valueOf(NUMBER_COUNT)).subtract(BigInteger.valueOf(NUMBER_COUNT));
        System.out.printf("V = %d \n", V);
    }
}
