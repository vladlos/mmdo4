package Methods;

import CalculationFunction.Function;
import matrix.Matrix;

public class NewtonModificated extends Newton{
    private int n;
    private int s;

    public NewtonModificated(Function function, Function[] grad, Function[][] gess, int s) {
        super(function, grad, gess);
        n = grad.length;
        this.s = s;
    }

    @Override
    public double[] findMin(double[] x0, double h0, double eps) {
        resetCounter();
        countCalc = 0;
        countIter = 0;

        double[] valueGrad = calculateGrad(x0);
        int n = grad.length;

        if (norma(valueGrad) > eps) {
            double[] x = null;
            double[][] valueGess;
            double[][] invertValueGess = null;

            do {
                if (countIter % s == 0) {
                    x = x0.clone();
                    valueGess = calculateGess(x0);
                    invertValueGess = valueGess.clone();
                    Matrix.invert(invertValueGess);
                }

                double[] w = new double[n];
                for (int i = 0; i < n; i++ ) {
                    w[i] = 0;
                    for (int j = 0; j < n; j++) {
                        w[i] = w[i] + invertValueGess[i][j] * valueGrad[j];
                    }
                }

                double h = find_h(x, w, h0, eps);

                for (int i = 0; i < n; i++) {
                    x0[i] = x[i] - h * w[i];
                }

                valueGrad = calculateGrad(x0);
                countIter++;
            } while (norma(valueGrad) >= eps);
        }

        countCalc += getCountFunctCalc();

        return x0;
    }

    private double find_h(double[] x0, double[] valueGrad, double h0, double eps){
        double h = 0;
        double f1, f2;
        double[] x1;
        double[] x2 = new double[n];

        f1 = getValueFunction(x0);
        do {
            h0 /= 2;
            for (int i = 0; i < n; i++){
                x2[i] = x0[i] - h0 * valueGrad[i];
            }
            f2 = getValueFunction(x2);

        }while (!( (f1 > f2) || (h0 < eps) ));

        if ( h0 > eps ){
            do {
                x1 = x2.clone();
                f1 = f2;
                h = h + h0;
                for (int i = 0; i < n; i++){
                    x2[i] = x1[i] - h * valueGrad[i];
                }
                f2 = getValueFunction(x2);
            }while ( f1 >= f2 );

            double hA = h - 2 * h0;
            double hB = h;
            double delta = eps / 3;
            do {
                double h1 = (hA + hB - delta) / 2;
                double h2 = (hA + hB + delta) / 2;
                for (int i = 0; i < n; i++){
                    x1[i] = x0[i] - h1 * valueGrad[i];
                    x2[i] = x0[i] - h2 * valueGrad[i];
                }
                f1 = getValueFunction(x1);
                f2 = getValueFunction(x2);
                if ( f1 <= f2 ){
                    hB = h2;
                }
                else{
                    hA = h1;
                }
            }while((hB - hA) >= eps);
            h = (hA + hB) / 2;
        }
        return h;
    }
}
