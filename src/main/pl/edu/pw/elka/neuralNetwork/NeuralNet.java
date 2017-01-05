package pl.edu.pw.elka.neuralNetwork;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import pl.edu.pw.elka.model.enumerate.ActivationFunctionType;

import java.util.*;
import java.util.function.Function;

@Getter
public class NeuralNet {
    private int inputVectorDimension;
    private int outputVectorDimension;
    private List<List<Neuron>> layerList;
    private Function<Double, Double> activationFunction;

    public NeuralNet(int inputVectorDimension, int outputVectorDimension, List<Integer> hiddenLayerSizes, Function<Double, Double> activationFunction) {
        this.inputVectorDimension = inputVectorDimension;
        this.outputVectorDimension = outputVectorDimension;
        this.activationFunction = activationFunction;
        createNeurons(hiddenLayerSizes, activationFunction);
    }

    private void createNeurons(List<Integer> hiddenLayerSizes, Function<Double,Double> activationFunction) {
        layerList = new ArrayList<>();
        for (int i = 0; i < hiddenLayerSizes.size(); ++i) {
            List<Neuron> layer = new ArrayList<>();
            for(int j = 0; j < hiddenLayerSizes.get(i); ++j) {
                Neuron neuron = Neuron.builder()
                        .inputList(new ArrayList<>())
                        .weightList(generateRandomWeights(i == 0 ? inputVectorDimension : hiddenLayerSizes.get(i-1) ))
                        .activationFunction(activationFunction)
                        .bias(0)
                        .build();
                layer.add(neuron);
            }
            layerList.add(layer);
        }
        List<Neuron> lastLayer = new ArrayList<>();
        for(int i = 0; i < outputVectorDimension; ++i) {
            Neuron neuron = Neuron.builder()
                                  .inputList(new ArrayList<>())
                                  .weightList(generateRandomWeights(hiddenLayerSizes.get(hiddenLayerSizes.size() - 1)))
                                  .activationFunction(ActivationFunctionType.getActivationFunction(ActivationFunctionType.LINEAR))
                                  .bias(0)
                                  .build();
            lastLayer.add(neuron);
        }
        layerList.add(lastLayer);
    }

    public void resetNeuronsWeights(){
        for (int i = 0; i < layerList.size(); i++) {
            List<Neuron> neurons = layerList.get(i);
            for (int j = 0; j < neurons.size(); j++) {
                Neuron neuron = neurons.get(j);
                neuron.setWeightList(generateRandomWeights(i == 0 ? inputVectorDimension : layerList.get(i-1).size() ));
            }
        }
    }

    private List<Double> generateRandomWeights(int size) {
        List<Double> randomWeights = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < size; ++i) {
            randomWeights.add(2 * (random.nextDouble() - 0.5));
        }
        return randomWeights;
    }

    public List<Double> getResponse(List<Double> inputVector){
        if(inputVector.size() != inputVectorDimension){
            throw new InputMismatchException();
        }

        List<Double> previousLayerOutput = inputVector;
        for(List<Neuron> layer : layerList){
            List<Double> currentLayerOutput = new ArrayList<>();
            for(Neuron neuron : layer){
                neuron.setInputList(previousLayerOutput);
                currentLayerOutput.add(neuron.calculateOutput());
            }
            previousLayerOutput = currentLayerOutput;
        }

        return  previousLayerOutput;
    }
}
