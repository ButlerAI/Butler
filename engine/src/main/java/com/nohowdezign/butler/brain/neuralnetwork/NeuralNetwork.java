package com.nohowdezign.butler.brain.neuralnetwork;

import java.util.ArrayList;

/**
 * @author Noah Howard
 */
public class NeuralNetwork {
    private int numberOfInputs = 4;
    private int numberOfHiddenNeurons = 8;
    private int numberOfOutputs = 4;

    public void forward(ArrayList<ArrayList<Double>> inputs) {
        //
    }

    private double getActivatedValue(double valueToActivate) {
        return 1 / (1 + Math.pow(Math.E, (-1 * valueToActivate)));
    }

}
