package CalculationFunction;

public class ConvertorFunction {

    public static String FunctToJSFunct(String input) {
        String res = input;

        String pattern = ("(\\w+|\\(.*\\))\\^(\\w+|\\(.*\\))");
        res = res.replaceAll(pattern, "pow($1,$2)");

        pattern = "pow";
        res = res.replaceAll(pattern, "Math.pow");


        pattern = "abs";
        res = res.replaceAll(pattern, "Math.abs");

        pattern = "exp";
        res = res.replaceAll(pattern, "Math.exp");

        pattern = "sqrt";
        res = res.replaceAll(pattern, "Math.sqrt");

        return res;
    }

    public static void main(String[] args) {
        String funct = "2*(6*x0^2*pow(2*x0^2+x1^2+1, 3/2)+x1^2+1)/pow(2*x0^2+x1^2+1,3/2)";
        System.out.println(FunctToJSFunct(funct));
    }

}
