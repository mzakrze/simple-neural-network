package pl.edu.pw.elka.model.enumerate;

import java.util.function.Function;

public enum ActivationFunctionType {
    SIGMOID_E_X("funkcja unipolarna"), LINEAR("funkcja liniowa"), SIGMOID_ARCTAN("arc tangens");
    private String desciption;

    ActivationFunctionType(String desciption){
        this.desciption = desciption;
    }

    public static Function<Double, Double> getActivationFunction(ActivationFunctionType activationFunctionType) {
        switch(activationFunctionType) {
            case LINEAR:
                return aDouble -> aDouble;
            case SIGMOID_E_X:
                return aDouble -> 1.0 / (1.0 + Math.pow(Math.E, -aDouble));
            case SIGMOID_ARCTAN:
                return aDouble -> Math.atan(aDouble);
            default:
                throw new IllegalStateException("Illegal activation function!");
        }
    }

    public String toString(){
        return desciption;
    }
}
