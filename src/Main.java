import CalculationFunction.Function;
import Methods.*;

import java.util.Arrays;

public class Main {
    public static void test(Minimize minimize, Function function, double[] point, double eps) {
        System.out.println("-------------- >>>> " + minimize.getClass() + " <<<< --------------------");
        double[] minPoint = minimize.findMin(point.clone(), 1, eps);
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
        //test(minimize, function, point, 0.0001);
        //test(minimize, function, point, 0.00000001);


        point = new double[]{1, 1};
        function = new Function("3*pow(x0,2)+pow(x1,2)+exp(pow(x0,2)+pow(x1,2))-2*x0+x1");
        grad = new Function[]{
                new Function("2*x0*(exp(pow(x0,2)+pow(x1,2))+3)-2"),
                new Function("2*x1*(exp(pow(x0,2)+pow(x1,2))+1)+1")
        };

        function = new Function("pow(x0,2)-2*x0*x1+3*pow(x1,2)+x0-4*x1");
        grad = new Function[]{
                new Function("2*x0-2*x1+1"),
                new Function("-2*x0+6*x1-4")
        };

        Minimize[] methods = new Minimize[]{
                new MNS(function, grad),
                new Zeydel(function),
        };

        for (Minimize min : methods) {
            test(min, function, point, 0.0001);
            test(min, function, point, 0.00000001);
        }

        point = new double[]{1, 1};
        function = new Function("3*pow(x0,2)+pow(x1,2)+exp(pow(x0,2)+pow(x1,2))-2*x0+x1");
        grad = new Function[]{
                new Function("2*x0*(exp(pow(x0,2)+pow(x1,2))+3)-2"),
                new Function("2*x1*(exp(pow(x0,2)+pow(x1,2))+1)+1")
        };
        Function[][] gess = {
                {new Function("2*((2*pow(x0,2)+1)*exp(pow(x0,2)+pow(x1,2))+3)"), new Function("4*x0*x1*exp(pow(x0,2)+pow(x1,2))")},
                {new Function("4*x0*x1*exp(pow(x0,2)+pow(x1,2))"), new Function("2*((2*pow(x1,2)+1)*exp(pow(x0,2)+pow(x1,2))+1)")}
        };
        methods = new Minimize[]{
                new NewtonClassic(function, grad, gess),
                new NewtonModificated(function, grad, gess, 1)
        };

        for (Minimize min : methods) {
            test(min, function, point, 0.0001);
            test(min, function, point, 0.00000001);
        }

    }
}

