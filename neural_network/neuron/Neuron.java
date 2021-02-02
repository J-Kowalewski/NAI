package neural_network.neuron;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Neuron  {
    double[] x;
    double[] w;
    double bias;
    boolean activated = false;
    double output = 0;
    Neuron parent1 = null;
    Neuron parent2 = null;
    Function function = new ActivationFunction();
    int graphX=0;
    int graphY=0;

    public int getGraphX() {
        return graphX;
    }

    public void setGraphX(int graphX) {
        this.graphX = graphX;
    }

    public int getGraphY() {
        return graphY;
    }

    public void setGraphY(int graphY) {
        this.graphY = graphY;
    }

    public Neuron(double[] x, double[] w, double bias) {
        this.x = x;
        this.w = w;
        this.bias = bias;
    }
    public Neuron(Neuron parent1, Neuron parent2, double bias, double[]w){
        double[] inputs = new double[2];
        inputs[0] = parent1.activate(parent1.add());
        inputs[1] = parent2.activate(parent2.add());
        this.w = w;
        this.x = inputs;
        this.bias = bias;
        this.parent1 = parent1;
        this.parent2 = parent2;
    }
    public double add(){
        return this.x[0]*this.w[0] + this.x[1]*this.w[1] + this.bias;
    }
    public double activate(double x){
        this.activated = true;
        this.output = BigDecimal.valueOf(function.calculate(x)).setScale(4, RoundingMode.HALF_UP).doubleValue();
        return output;
    }

    public double getOutput() {
        return output;
    }

    public Neuron getParent1() {
        return parent1;
    }

    public Neuron getParent2() {
        return parent2;
    }

    public double[] getW() {
        return w;
    }
}
