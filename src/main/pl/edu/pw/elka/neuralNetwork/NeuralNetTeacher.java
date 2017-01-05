package pl.edu.pw.elka.neuralNetwork;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mariusz on 2017-01-22.
 */
public class NeuralNetTeacher {
    public static final int EPOCH_STEPS = 10000;
    public static final double TEACH_PARAMETER = 0.02;
    private NeuralNet neuralNet;
    private List<Double> input;
    private List<Double> output;

    public void teach(NeuralNet neuralNet, List<Double> input, List<Double> output) {
        this.neuralNet = neuralNet;
        this.input = input;
        this.output = output;
        neuralNet.getResponse(input);

        List<Double> outLayerError = new ArrayList<>();
        List<Neuron> outLayer = neuralNet.getLayerList().get(neuralNet.getLayerList().size() - 1);

        for(int i = 0; i < outLayer.size(); i++) {
            Neuron n = outLayer.get(i);
            double neuronOutput = n.calculateOutput();
            //double error = (1.0 - neuronOutput) * neuronOutput * (output.get(i) - neuronOutput);
            double error = (output.get(i) - neuronOutput);
            outLayerError.add(error);
            List<Double> newWeights = new ArrayList<>();
            for(int j = 0; j < n.getWeightList().size(); j++){
                newWeights.add(n.getWeightList().get(j) + TEACH_PARAMETER * error * n.getInputList().get(j));
            }
            n.setWeightList(newWeights);
        }

        List<Double> previousLayerError = outLayerError;
        List<Neuron> previousLayer = outLayer;
        for(int i = neuralNet.getLayerList().size() - 2; i >= 0; i--){
            List<Double> layerError = new ArrayList<>();
            List<Neuron> layer = neuralNet.getLayerList().get(i);
            for (int j = 0; j < layer.size(); j++) {
                Neuron neuron = layer.get(j);
                double sum = 0.0;
                for(int h = 0; h < previousLayer.size(); h++) {
                    sum += previousLayerError.get(h) * previousLayer.get(h).getWeightList().get(j);
                }
                double error = sum * (1 - neuron.calculateOutput()) * neuron.calculateOutput();
                layerError.add(error);

                List<Double> newNeuronWeights = new ArrayList<>();
                for(int k = 0; k < neuron.getWeightList().size(); k++){
                    newNeuronWeights.add(neuron.getWeightList().get(k) + TEACH_PARAMETER * error * neuron.getInputList().get(k));
                }
                neuron.setWeightList(newNeuronWeights);
            }
            previousLayer = layer;
            previousLayerError = layerError;
        }
    }
}