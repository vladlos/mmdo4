package Methods;

import CalculationFunction.Function;

public class Zeydel extends Minimize {
    public Zeydel(Function function) {
        super(function);
    }

    @Override
    public double[] findMin(double[] x0, double h0, double eps) {
        super.resetCounter();
        countCalc = 0;
        countIter = 0;

        int n = x0.length;
        double[] xInt = x0.clone();
        double[] rizn = new double[n];
        do {
            double[] xExt = xInt.clone();
            for (int i = 0; i < n; i++) {
                double h;
                double fx1;
                double fx;
                double[] x = xInt.clone();
                fx = getValueFunction(x);
                double[] y1 = x.clone();
                y1[i] = y1[i] + 3 * eps;
                double[] y2 = x.clone();
                y2[i] = y2[i] - 3 * eps;
                double f1 = getValueFunction(y1);
                double f2 = getValueFunction(y2);
                double z = Math.signum(f2 - f1);
                do {
                    h = FindHCoordinate(xInt, i, z, h0, eps);
                    xInt[i] = x[i] + h * z;
                    fx1 = getValueFunction(xInt);
                } while (!((fx1 < fx) || (h < eps / 2)));
            }
            for (int i = 0; i < n; i++) {
                rizn[i] = xInt[i] - xExt[i];
            }
            countIter++;
        } while (norma(rizn) >= eps);
        countCalc = getCountFunctCalc();
        return xInt;
    }

    private double FindHCoordinate(double[] x0, int i, double iSign, double h0, double eps) {
        double h = 0;
        double[] x2 = x0.clone();
        double[] x1;
        double f1 = getValueFunction(x0);
        double f2;
        do {
            h0 /= 2;
            x2[i] = x0[i] + iSign * h0;
            f2 = getValueFunction(x2);
        } while ((f1 <= f2) && (h0 >= eps));

        if (h0 > eps) {
            do {
                x1 = x2.clone();
                f1 = f2;
                h = h + h0;
                x2[i] = x1[i] + iSign * h;
                f2 = getValueFunction(x2);
            } while (f1 >= f2);


            double hA = h - 2 * h0;
            double hB = h;
            double sigma = eps / 3;

            do {
                double h1 = (hA + hB - sigma) / 2;
                double h2 = (hA + hB + sigma) / 2;
                x1[i] = x0[i] + iSign * h1;
                x2[i] = x0[i] + iSign * h2;
                f1 = getValueFunction(x1);
                f2 = getValueFunction(x2);
                if (f1 <= f2) {
                    hB = h2;
                } else {
                    hA = h1;
                }
            } while (hB - hA >= eps);
            h /= 2;
        }
        return h;
    }
}
