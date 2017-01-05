package pl.edu.pw.elka.model;

import org.apache.commons.lang3.tuple.Pair;
import pl.edu.pw.elka.model.enumerate.ActivationFunctionType;
import pl.edu.pw.elka.neuralNetwork.NeuralNet;
import pl.edu.pw.elka.neuralNetwork.NeuralNetTeacher;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private NeuralNet neuralNet;
    private int xSize;
    private int ySize;
    private NeuralNetTeacher neuralNetTeacher;

    public Model(int xSize, int ySize, int inputVectorDimension, int outputVectorDimension, List<Integer> hiddenLayerSizes, ActivationFunctionType activationFunctionType) {
        this.xSize = xSize;
        this.ySize = ySize;
        neuralNetTeacher = new NeuralNetTeacher();
        neuralNet = new NeuralNet(inputVectorDimension, outputVectorDimension, hiddenLayerSizes, ActivationFunctionType.getActivationFunction(activationFunctionType));
    }

    public void teachForGivenTrainingExamples(List<Double> inputsList, List<Double> outputs){
        neuralNetTeacher.teach(neuralNet,inputsList,outputs);
    }

    public Pair<Double,Double>[][] getResultForFullMatrix() {
        Pair<Double,Double>[][] resultsFromNeuralNet = new Pair[xSize][ySize];
        for(int x = 0; x < xSize; x++){
            for(int y = 0; y <ySize; y++){
                List<Double> inputVector = new ArrayList<>();
                inputVector.add((double)x / (double) xSize);
                inputVector.add((double)y / (double) ySize);
                List<Double> outputVector = neuralNet.getResponse(inputVector);
                resultsFromNeuralNet[x][y] = Pair.of(outputVector.get(0), outputVector.get(1));
            }
        }
        return convertToRoundedValues(resultsFromNeuralNet);
    }

    private Pair<Double,Double>[][] convertToRoundedValues(Pair<Double, Double>[][] matrix) {
        Pair<Double,Double>[][] toRet = new Pair[xSize][ySize];
        for(int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                double xDiff = Math.abs(1.0 - matrix[x][y].getLeft());
                double yDiff = Math.abs(1.0 - matrix[x][y].getRight());
                toRet[x][y] = xDiff < yDiff ? Pair.of(1.0, 0.0) : Pair.of(0.0, 1.0);
            }
        }
        return toRet;
    }
}
