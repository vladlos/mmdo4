package Methods;

import CalculationFunction.Function;

import java.util.Map;

public class MNS extends Minimize{
    private Function[] grad;
    private int n;


    public MNS(Function function, Function[] grad) {
        super(function);
        this.grad = grad;
        this.n = grad.length;
    }

    private double[] getValueGrad(double[] tmp){
        Map<String, Double> values = Function.convertArrayToMap(tmp);

        double[] valueGrad = new double[grad.length];
        for (int j = 0; j < grad.length; j++) {
            valueGrad[j] = grad[j].calculate(values);
        }

        countCalc += grad.length;
        return valueGrad;
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
            return h;
        }
        else {
            return h0;
        }
    }

    @Override
    public double[] findMin(double[] x0, double h0, double eps){
        super.resetCounter();
        countCalc = 0;
        countIter = 0;

        double[] valueGrad = getValueGrad(x0);
        double[] x;
        double[] rizn = new double[n];
        if (norma(valueGrad) > eps)
        {
            do {
                x = x0.clone();
                double h = find_h(x0,valueGrad,h0, eps);
                for (int i = 0; i < n; i++){
                    x0[i] = x[i] - h * valueGrad[i];
                }
                valueGrad = getValueGrad(x0);
                for (int i = 0; i < n; i++){
                    rizn[i] = x[i] - x0[i];
                }
                countIter++;
            }while ((norma(rizn) >= eps) && (norma(valueGrad) >= eps));
        }

        countCalc += getCountFunctCalc();
        return x0;
    }
}
