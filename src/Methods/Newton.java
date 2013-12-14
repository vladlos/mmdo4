package Methods;

import CalculationFunction.Function;

public abstract class Newton extends Minimize {
    protected Function[] grad;
    protected Function[][] gess;

    protected Newton(Function function, Function[] grad, Function[][] gess) {
        super(function);
        this.grad = grad;
        this.gess = gess;
    }

    protected double[] calculateGrad(double[] input) {
        double[] res = new double[grad.length];

        for (int j = 0; j < grad.length; j++) {
            res[j] = grad[j].calculate(input);
        }

        countCalc += grad.length;

        return res;
    }

    protected double[][] calculateGess(double[] input) {
        double[][] res = new double[gess.length][gess[0].length];

        for (int i = 0; i < gess.length; i++) {
            for (int j = 0; j < gess[i].length; j++) {
                res[i][j] = gess[i][j].calculate(input);
            }
        }

        countCalc += Math.pow(grad.length,2);

        return res;
    }
}
