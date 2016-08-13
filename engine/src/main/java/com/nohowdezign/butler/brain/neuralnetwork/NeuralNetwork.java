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

    public void forward(ArrayList<ArrayList<Double>> inputs) {
        ArrayList<ArrayList<Double>> initialSynapses = createSynapses(inputs, numberOfHiddenNeurons);
        System.out.println(initialSynapses);
        ArrayList<ArrayList<Double>> initialActivatedSynapses = activateSynapses(initialSynapses);
        System.out.println(initialActivatedSynapses);
        ArrayList<ArrayList<Double>> secondSynapses = createSynapses(initialActivatedSynapses, numberOfOutputs);
        ArrayList<ArrayList<Double>> finalValue = activateSynapses(secondSynapses);
        System.out.println(finalValue);
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
