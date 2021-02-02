package neural_network.neuron;

import neural_network.graph.GraphNode;

public class Main {
    public static void main(String[] args) {
        double[] val1 = {0,1};
        double[] weight1 ={3,4};
        Neuron neuron = new Neuron(val1,weight1,4);

        double[] val2 = {1,2};
        double[] weight2 ={4,5};
        Neuron neuron2 = new Neuron(val2,weight2,5);

        double[] weight3 = {1,2};
        Neuron neuron3 = new Neuron(neuron,neuron2,5,weight3);

        double[] weight4 = {5,6};
        Neuron neuron4 = new Neuron(neuron,neuron3,8, weight4);

        NeuronNetwork nn = new NeuronNetwork();
        nn.addNeuron(neuron);
        nn.addNeuron(neuron2);
        nn.addNeuron(neuron3);
        nn.addNeuron(neuron4);

        nn.getInputs();
        nn.getWeights();
        nn.getWeightedInputs();
        nn.getStates();
        nn.getParents(2);
        nn.getParents(1);
        nn.getParents(3);

        //System.out.println("\n"+nn.generateGraph(neuron3));

    }
}
