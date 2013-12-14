package Methods;

import CalculationFunction.Function;
import matrix.Matrix;

import java.util.Arrays;

public class NewtonClassic extends Newton{

    public NewtonClassic(Function function, Function[] grad, Function[][] gess) {
        super(function, grad, gess);
    }

    @Override
    public double[] findMin(double[] x0, double h0, double eps) {
        resetCounter();
        countCalc = 0;
        countIter = 0;

        double[] valueGrad = calculateGrad(x0);
        int n = grad.length;

        if (norma(valueGrad) >= eps) {
            do {
                double[] x = x0.clone();
                double[][] valueGess = calculateGess(x0);
                double[][] invertValueGess = valueGess.clone();
                Matrix.invert(invertValueGess);

                double[] w = new double[n];
                for (int i = 0; i < n; i++ ) {
                    w[i] = 0;
                    for (int j = 0; j < n; j++) {
                        w[i] = w[i] + invertValueGess[i][j] * valueGrad[j];
                    }
                    x0[i] = x[i] - w[i];
                }

                valueGrad = calculateGrad(x0);
                countIter++;
            } while (norma(valueGrad) >= eps);
        }

        countCalc += getCountFunctCalc();

        return x0;
    }
}
