package com.nohowdezign.butler.brain.neuralnetwork;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Noah Howard
 */
public class NeuralNetwork {
    private int numberOfInputs = 4;
    private int numberOfHiddenNeurons = 8;
    private int numberOfOutputs = 4;

    public NeuralNetwork(int hiddenNeurons, int outputs) {
        this.numberOfHiddenNeurons = hiddenNeurons;
        this.numberOfOutputs = outputs;
    }

    public NeuralNetwork() {}

    public ArrayList<ArrayList<Double>> forward(ArrayList<ArrayList<Double>> inputs) {
        ArrayList<ArrayList<Double>> initialSynapses = createSynapses(inputs, numberOfHiddenNeurons);
        ArrayList<ArrayList<Double>> initialActivatedSynapses = activateSynapses(initialSynapses);
        ArrayList<ArrayList<Double>> secondSynapses = createSynapses(initialActivatedSynapses, numberOfOutputs);
        ArrayList<ArrayList<Double>> finalValue = activateSynapses(secondSynapses);
        return finalValue;
    }

    public void trainNeuralNetwork(ArrayList<Double> output, ArrayList<Double> expected) {
        ArrayList<Double> marginOfError = new ArrayList<>();
        for (int i = 0; i < output.size(); i++) {
            // This will use the derivative of sigmoid to estimate the error in the function, that can then be
            // backpropogated to get better values for weights
            double deltaSum = (output.get(i) * (1 - output.get(i))) * (expected.get(i) - output.get(i));
            System.out.println(deltaSum);
        }
    }

    private ArrayList<ArrayList<Double>> createSynapses(ArrayList<ArrayList<Double>> inputs, int iterations) {
        ArrayList<ArrayList<Double>> synapses = new ArrayList<>();
        for(ArrayList<Double> input : inputs) {
            ArrayList<ArrayList<Double>> weightedValues = new ArrayList<>();
            for(Double inputValue : input) {
                ArrayList<Double> weightsTimesInputs = new ArrayList<>();
                for(int i = 0; i < iterations; i++) {
                    Random generator = new Random();
                    double weight = generator.nextDouble() * 1;
                    weightsTimesInputs.add(inputValue * weight);
                }
                weightedValues.add(weightsTimesInputs);
            }
            ArrayList<Double> synapseValues = new ArrayList<>();
            for(ArrayList<Double> weightedInputs : weightedValues) {
                for(int i = 0; i < weightedInputs.size(); i++) {
                    try {
                        double newValue = synapseValues.get(i) + weightedInputs.get(i);
                        synapseValues.set(i, newValue);
                    } catch(IndexOutOfBoundsException e) {
                        synapseValues.add(weightedInputs.get(i));
                    }
                }
            }
            synapses.add(synapseValues);
        }
        return synapses;
    }

    private ArrayList<ArrayList<Double>> activateSynapses(ArrayList<ArrayList<Double>> synapses) {
        ArrayList<ArrayList<Double>> activatedSynapses = new ArrayList<>();
        for(ArrayList<Double> synapseForInput : synapses) {
            ArrayList<Double> activatedInput = new ArrayList<>();
            for(Double synapse : synapseForInput) {
                activatedInput.add(getActivatedValue(synapse));
            }
            activatedSynapses.add(activatedInput);
        }
        return activatedSynapses;
    }

    private double getActivatedValue(double valueToActivate) {
        return 1 / (1 + Math.pow(Math.E, (-1 * valueToActivate)));
    }

}
