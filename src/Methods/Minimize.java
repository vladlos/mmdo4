package Methods;

import CalculationFunction.Function;

public abstract class Minimize {
    private Function function;
    protected int countIter = 0;
    protected int countCalc = 0;

    public String getFunction() {
        return function.getFunct();
    }

    public int getCountCalc() {
        return countCalc;
    }

    public int getCountIter() {
        return countIter;
    }

    protected Minimize(Function function) {
        this.function = function;
    }

    protected double getValueFunction(double[] tmp) {
        return function.calculate(tmp);
    }

    protected int getCountFunctCalc() {
        return function.getCountCalc();
    }

    protected void resetCounter() {
        function.resetCounter();
    }

    protected double norma(double[] tmp) {
        double res = 0;
        for (double aTmp : tmp) {
            res += Math.pow(aTmp, 2);
        }
        return Math.sqrt(res);
    }

    public abstract double[] findMin(double[] x0, double h0, double eps);
}
