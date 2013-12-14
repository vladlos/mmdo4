package CalculationFunction;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

public class Function {
    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine = mgr.getEngineByName("JavaScript");

    private int countCalc = 0;

    public int getCountCalc() {
        return countCalc;
    }

    public void resetCounter() {
        countCalc = 0;
    }

    public static Map<String, Double> convertArrayToMap(double[] array) {
        int i = 0;
        Map<String, Double> values = new HashMap<>();
        for (double value : array) {
            values.put("x"+i++, value);
        }
        return values;
    }

    private String funct;

    public Function(String funct) {
        this.funct = ConvertorFunction.FunctToJSFunct(funct);
    }

    public String getFunct() {
        return funct;
    }

    public void setFunct(String funct) {
        this.funct = funct;
    }

    public double calculate(Map<String, Double> values) {
        double res = 0;
        try {
            for (Map.Entry<String, Double> entry : values.entrySet()) {
                engine.put(entry.getKey(), entry.getValue());
            }
            res = Double.parseDouble(String.valueOf(engine.eval(funct)));
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }
        countCalc++;
        return res;
    }

    public double calculate(double[] input) {
        return calculate(convertArrayToMap(input));
    }

    public static void main(String[] args) {
        Function function = new Function("100*pow(x1-x0^2,2)+pow(1-x0,2)");
        System.out.println(function.getFunct());
        System.out.println(function.calculate(new double[]{1, 1}));
    }
}
