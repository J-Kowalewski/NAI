package neural_network.neuron;

public class ActivationFunction implements Function {
    public double calculate(double x){
        return 1/Math.pow((1+Math.abs(x)),2);
    }
}
