import CalculationFunction.Function;
import Methods.*;

import java.util.Arrays;

public class Main {
    public static void test(Minimize minimize, Function function, double[] point, double eps, double h) {
        System.out.println("-------------- >>>> " + minimize.getClass() + " <<<< --------------------");
        double[] minPoint = minimize.findMin(point.clone(), h, eps);
        double valueMin = function.calculate(minPoint);
        System.out.println("Function = " + minimize.getFunction());
        System.out.println("MinPoint = " + Arrays.toString(minPoint) + "\t");
        System.out.println("Values function = " + valueMin);
        System.out.println("Count calc = " + minimize.getCountCalc());
        System.out.println("Count iter = " + minimize.getCountIter());
        System.out.println("Esp = " + eps);

    }

    public static void main(String[] args) {
        double[] point = new double[]{0, 0};
        Function function = new Function("100*pow(x1-pow(x0,2),2)+pow(1-x0,2)");
        Function[] grad = {
                new Function("-400*x0*(-1*pow(x0,2)+x1)+2*x0-2"),
                new Function("-200*pow(x0,2)+200*x1")
        };

        Minimize minimize = new MNS(function, grad);
        test(minimize, function, point, 0.0001, 1);
        test(minimize, function, point, 0.00000001, 1);


        point = new double[]{0, 2};
        function = new Function("40*pow(x0,2)+20*x0*x1+30*pow(x1,2)-10*x0+x1");
        grad = new Function[]{
                new Function("20*x1+80*x0-10"),
                new Function("60*x1+20*x0+1")
        };

        Minimize[] methods = new Minimize[]{
                new MNS(function, grad),
                new Zeydel(function),
        };

        for (Minimize min : methods) {
            test(min, function, point, 0.0001, 1);
            test(min, function, point, 0.00000001, 1);
        }

        point = new double[]{1, 1};
        function = new Function("x0^4+3*x1^4+sqrt(2*x0^2+x1^2+1)-4*x0+6*x1");
        grad = new Function[]{
                new Function("4*x0^3+(2*x0)/sqrt(2*x0^2+x1^2+1)-4"),
                new Function("x1/sqrt(2*x0^2+x1^2+1)+12*x1^3+6")
        };
        Function[][] gess = {
                {new Function("2*(6*x0^2*pow(2*x0^2+x1^2+1, 3/2)+x1^2+1)/pow(2*x0^2+x1^2+1,3/2)"), new Function("-1*(2*x0*x1)/pow((2*x0^2+x1^2+1),3/2)")},
                {new Function("-1*(2*x0*x1)/pow((2*x0^2+x1^2+1),3/2)"), new Function("(2*x0^2+1)/pow((2*x0^2+x1^2+1),3/2)+36*x1^2")}
        };
        methods = new Minimize[]{
                new NewtonClassic(function, grad, gess),
                new NewtonModificated(function, grad, gess, 1)
        };


        double h = 0.3;
        test(methods[0], function, point, 1E-4, h);
        test(methods[0], function, point, 1E-8, h);
        test(methods[1], function, point, 1E-4, h);
        test(methods[1], function, point, 1E-7, h);
    }
}

