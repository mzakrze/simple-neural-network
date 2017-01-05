package pl.edu.pw.elka.controller;

import org.apache.commons.lang3.tuple.Pair;
import pl.edu.pw.elka.model.enumerate.ActivationFunctionType;
import pl.edu.pw.elka.model.Model;
import pl.edu.pw.elka.neuralNetwork.NeuralNet;
import pl.edu.pw.elka.neuralNetwork.NeuralNetTeacher;
import pl.edu.pw.elka.view.View;
import pl.edu.pw.elka.view.WorkPanel;
import pl.edu.pw.elka.view.enumerate.FieldType;

import java.util.*;


public class Controller {
    private int xSize;
    private int ySize;
    private final int inputVectorDimension = 2;
    private final int outputVectorDimension = 2;
    private View view;
    private Model model;

    public Controller() {
        view = new View();
        view.createAndShowGUI(this);
    }

    public void createModelForGivenParameters(int xSize, int ySize, List<Integer> hiddenLayerSizes, ActivationFunctionType activationFunctionType) {
        this.xSize = xSize;
        this.ySize = ySize;
        model = new Model(xSize, ySize, inputVectorDimension, outputVectorDimension, hiddenLayerSizes, activationFunctionType);
    }

    public FieldType[][] calculateResultBoard() {
        Pair<Double, Double>[][] result = model.getResultForFullMatrix();
        return mapBoardFromVectorToFieldType(result);
    }

    public void teachNetwork(WorkPanel.Field[][] inputBoard) {
        Map<List<Double>, List<Double>> exampleMap = new HashMap<>();

        for(int x = 0; x < inputBoard.length; x++){
            for(int y = 0; y <inputBoard[0].length; y++){
                if(inputBoard[x][y].getType() != FieldType.NONE){
                    List<Double> input = new ArrayList<>();
                    List<Double> output = new ArrayList<>();

                    input.add((double)x / (double) xSize);
                    input.add((double)y / (double) ySize);

                    output.add(((double) inputBoard[x][y].getType().getVector().getLeft()));
                    output.add(((double) inputBoard[x][y].getType().getVector().getRight()));

                    exampleMap.put(input, output);
                }
            }
        }

        for(int i = 0; i < NeuralNetTeacher.EPOCH_STEPS; ++i) {
            System.out.println("teach step: " + (i + 1) + " / " + NeuralNetTeacher.EPOCH_STEPS);
            List<List<Double>> keys = new ArrayList<>(exampleMap.keySet());
            Collections.shuffle(keys);

            for (List<Double> keyList : keys) {
                model.teachForGivenTrainingExamples(keyList, exampleMap.get(keyList));
            }
        }
    }

    private FieldType[][] mapBoardFromVectorToFieldType(Pair<Double, Double>[][] board) {
        FieldType[][] toReturnBoard = new FieldType[xSize][ySize];
        for(int x = 0; x < xSize; x++){
            for(int y = 0; y < ySize; y++){
                toReturnBoard[x][y] = FieldType.getFieldTypeForVector(board[x][y]);
            }
        }
        return  toReturnBoard;
    }
}
