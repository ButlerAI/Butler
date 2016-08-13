package com.nohowdezign.butler.brain.neuralnetwork;

import org.junit.Test;

import java.util.ArrayList;

/**
 * @author Noah Howard
 */
public class NeuralNetworkTest {
    private NeuralNetwork neuralNetwork = new NeuralNetwork();

    @Test
    public void forward() {
        ArrayList<ArrayList<Double>> inputs = new ArrayList<>();
        ArrayList<Double> input = new ArrayList<>();
        input.add(0.0);
        input.add(0.0);
        input.add(0.0);
        input.add(0.0);
        inputs.add(input);
        neuralNetwork.forward(inputs);
    }

}