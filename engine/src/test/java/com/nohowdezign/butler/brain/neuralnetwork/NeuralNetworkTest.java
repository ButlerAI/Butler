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
        input.add(1.0);
        input.add(0.0);
        input.add(0.0);
        input.add(0.3333);
        inputs.add(input);
        ArrayList<ArrayList<Double>> outputs = neuralNetwork.forward(inputs);
        System.out.println(outputs);
        ArrayList<Double> expected = new ArrayList<>();
        expected.add(1.0);
        expected.add(0.0);
        expected.add(0.0);
        expected.add(0.0);
        for(ArrayList<Double> output : outputs) {
            neuralNetwork.trainNeuralNetwork(output, expected);
        }
    }

}