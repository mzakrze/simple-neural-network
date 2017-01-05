package pl.edu.pw.elka.neuralNetwork;

import lombok.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Neuron {
    private List<Double> inputList;
    private List<Double> weightList;
    private Function<Double, Double> activationFunction;
    private double bias;

    public double calculateOutput(List<Double> input) {
        if(weightList.size() != input.size())
            throw new InputMismatchException();
        double sum = 0.0;
        for(int i = 0; i < weightList.size(); i++){
            sum += weightList.get(i) * input.get(i);
        }
        return activationFunction.apply(sum + bias);
    }

    // TODO: 1/20/17 zrobić żeby z inputList wyliczała i przerobić model tak żeby zawszeinput uzupełniał 
    public double calculateOutput() {
        if(weightList.size() != inputList.size())
            throw new InputMismatchException();
        double sum = 0.0;
        for(int i = 0; i < weightList.size(); i++){
            sum += weightList.get(i) * inputList.get(i);
        }
        return activationFunction.apply(sum + bias);
    }
}
