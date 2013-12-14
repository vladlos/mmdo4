package CalculationFunction;

public class ConvertorFunction {

    public static String FunctToJSFunct(String input) {
        String res = input;

        String pattern = "pow";
        res = res.replaceAll(pattern, "Math.pow");

//        pattern = ("(\\w+|\\(.*\\))\\^(\\w+|\\(.*\\))");
//        res = res.replaceAll(pattern, "Math.pow($1,$2)");

        pattern = "abs";
        res = res.replaceAll(pattern, "Math.abs");

        pattern = "exp";
        res = res.replaceAll(pattern, "Math.exp");

        pattern = "sqrt";
        res = res.replaceAll(pattern, "Math.sqrt");

        return res;
    }

    public static void main(String[] args) {
        String funct = "100*pow(x1-x0^2,2)+pow(1-x0,2)";
        FunctToJSFunct(funct);
    }

}
