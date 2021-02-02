package neural_network.neuron;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NeuronNetwork {
    List<Neuron> neurons = new ArrayList<>();

    public void addNeuron(Neuron neuron){
        neurons.add(neuron);
    }

    Neuron getNeuron(int id){
        return neurons.get(id);
    }

    public List<Neuron> getNeurons(){
        return neurons;
    }

    void getWeightedInputs(){
        System.out.println("Weighted Inputs");
        neurons.forEach(neuron -> System.out.println("N:" + neurons.indexOf(neuron) + " " + neuron.add()));
    }

    void getInputs(){
        System.out.println("Inputs");
        neurons.forEach(neuron -> System.out.println("N:" + neurons.indexOf(neuron) + " " + Arrays.toString(neuron.x)));
    }

    void getWeights(){
        System.out.println("Weights");
        neurons.forEach(neuron -> System.out.println("N:" + neurons.indexOf(neuron) + " " + Arrays.toString(neuron.w)));
    }

    void getStates(){
        System.out.println("States");
        neurons.forEach(neuron -> System.out.println("N:" + neurons.indexOf(neuron) + " " + neuron.activated));
    }
    void getParents(int id){
        Neuron neuron = neurons.get(id);
            if(neuron.parent1!=null) {
                System.out.println("N:" + neurons.indexOf(neuron.parent1) + "-");
                System.out.println("    \\");
                System.out.println("     --"+" N:" + neurons.indexOf(neuron));
                System.out.println("    /");
                System.out.println("N:" + neurons.indexOf(neuron.parent2)+"-");
            }
            else{
                System.out.println("N:" + neurons.indexOf(neuron) + " no parents");
            }
    }
    public MutableValueGraph<Integer,Double> generateGraph(Neuron neuron) {
        int mainId = neurons.indexOf(neuron);

        MutableValueGraph<Integer, Double> weightedGraph = ValueGraphBuilder.directed().build();
        weightedGraph.addNode(mainId);
        List<Neuron> parentsList = new ArrayList<>();
        parentsList.add(neuron.parent1);
        parentsList.add(neuron.parent2);

        for (int i = 0; i < parentsList.size(); i++) {
            weightedGraph.addNode(neurons.indexOf(parentsList.get(i)));
            weightedGraph.putEdgeValue(neurons.indexOf(parentsList.get(i)), mainId, neurons.get(i).output);
        }
        return weightedGraph;
    }
}
