package neural_network.graph;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import neural_network.neuron.Neuron;
import neural_network.neuron.NeuronNetwork;

/**
 *
 * @author "Hovercraft Full of Eels", "Rodrigo Azevedo"
 *
 * This is an improved version of Hovercraft Full of Eels (https://stackoverflow.com/users/522444/hovercraft-full-of-eels)
 * answer on StackOverflow: https://stackoverflow.com/a/8693635/753012
 *
 * GitHub user @maritaria has made some performance improvements which can be found in the comment section of this Gist.
 */
public class GraphPanel extends JPanel {
    private final Color lineColor = new Color(44, 102, 230, 180);
    private final Color pointColor = new Color(100, 100, 100, 180);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);

    public GraphPanel(List<Double> scores) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        AffineTransform tx = new AffineTransform();

        Polygon arrowHead = new Polygon();
        arrowHead.addPoint( 0,5);
        arrowHead.addPoint( -5, -5);
        arrowHead.addPoint( 5,-5);

        int startingX=100;
        int startingY=200;

        double[] val1 = {0,1};
        double[] weight1 ={3,4};
        Neuron neuron1 = new Neuron(val1,weight1,4);

        double[] val2 = {1,2};
        double[] weight2 ={4,5};
        Neuron neuron2 = new Neuron(val2,weight2,5);

        double[] weight3 = {1,2};
        Neuron neuron3 = new Neuron(neuron1,neuron2,5,weight3);

        double[] weight4 = {5,6};
        Neuron neuron4 = new Neuron(neuron1,neuron3,8, weight4);

        double[] weight5 = {5,6};
        Neuron neuron5 = new Neuron(neuron3,neuron4,8, weight5);

        double[] weight6 = {5,6};
        Neuron neuron6 = new Neuron(neuron1,neuron4,8, weight5);

        double[] weight7 = {5,6};
        Neuron neuron7 = new Neuron(neuron4,neuron6,8, weight5);

        NeuronNetwork nn = new NeuronNetwork();
        nn.addNeuron(neuron1);
        nn.addNeuron(neuron2);
        nn.addNeuron(neuron3);
        nn.addNeuron(neuron4);
        nn.addNeuron(neuron5);
        nn.addNeuron(neuron6);
        nn.addNeuron(neuron7);
        List<Neuron> neurons = nn.getNeurons();

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        Stroke oldStroke = g2.getStroke();

        // draw white background
        g2.setColor(Color.WHITE);
        int padding = 25;
        int labelPadding = 25;
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);


        //punkty

        List<Point> graphPoints = new ArrayList<>();
//        graphPoints.add(new Point(startingX, startingY));
//        graphPoints.add(new Point(150, 150));
//        graphPoints.add(new Point(150, 250));
        for (Neuron n : neurons) {
            g2.setColor(Color.BLACK);
            int i = neurons.indexOf(n)+1;
            if(n.getParent1() == null){
                graphPoints.add(new Point(startingX,startingY));
                n.setGraphX(startingX);
                n.setGraphY(startingY);
                startingY=startingY+100;
            }
            else{
                n.setGraphX((n.getParent1().getGraphX()+n.getParent2().getGraphX())/2+50);
                n.setGraphY((n.getParent1().getGraphY()+n.getParent2().getGraphY())/2);
                if(i==6) n.setGraphY(n.getGraphY()-50);
                if(i==5) n.setGraphY(n.getGraphY()+50);
                graphPoints.add(new Point(n.getGraphX(),n.getGraphY()));
            }
            g2.setColor(pointColor);
            g2.drawString("N" + i+" "+n.getOutput(),n.getGraphX(),n.getGraphY());
            System.out.println(neurons.indexOf(n)+1+" "+n.getGraphX()+" "+n.getGraphY());
        }

        g2.setColor(Color.BLACK);
        g2.setStroke(oldStroke);
        for (Point graphPoint : graphPoints) {
            int pointWidth = 10;
            int x = graphPoint.x - pointWidth / 2;
            int y = graphPoint.y - pointWidth / 2;
            g2.fillOval(x, y, pointWidth, pointWidth);
        }
        //NAPISY

//        g2.drawString("N0",100,200);
//        g2.drawString("N1",150,150);
//        g2.drawString("N2",150,250);

        //  LINIE


        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
//        g2.drawLine(graphPoints.get(0).x,graphPoints.get(0).y,graphPoints.get(1).x,graphPoints.get(1).y);
//        g2.drawLine(graphPoints.get(0).x,graphPoints.get(0).y,graphPoints.get(2).x,graphPoints.get(2).y);

        for (Neuron n : neurons) {
            if(n.getParent1() != null){
                Line2D.Double line1 = new Line2D.Double(n.getParent1().getGraphX(),n.getParent1().getGraphY(),n.getGraphX(),n.getGraphY());
                Line2D.Double line2 = new Line2D.Double(n.getParent2().getGraphX(),n.getParent2().getGraphY(),n.getGraphX(),n.getGraphY());
                g2.draw(line1);
                g2.draw(line2);

                g2.setColor(Color.RED);
                int xCenter = (int) ((line1.x1+line1.x2)/2);
                int yCenter = (int) ((line1.y1+line1.y2)/2);
                g2.drawString(""+ n.getParent1().add(),xCenter,yCenter);

                xCenter = (int) ((line2.x1+line2.x2)/2);
                yCenter = (int) ((line2.y1+line2.y2)/2);
                g2.drawString(""+n.getParent2().add(),xCenter,yCenter);

                g2.setColor(lineColor);
                drawArrowHead(g2,tx,line1,arrowHead);
                drawArrowHead(g2,tx,line2,arrowHead);
            }
        }
    }

    private static void createAndShowGui() {
        List<Double> scores = new ArrayList<>();
        GraphPanel mainPanel = new GraphPanel(scores);
        mainPanel.setPreferredSize(new Dimension(800, 600));
        JFrame frame = new JFrame("DrawGraph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private void drawArrowHead(Graphics2D g2d,  AffineTransform tx, Line2D.Double line , Polygon arrowHead) {
        tx.setToIdentity();
        double angle = Math.atan2(line.y2-line.y1, line.x2-line.x1);

        tx.translate(line.x2, line.y2);
        tx.rotate((angle-Math.PI/2d));
        tx.translate(0,-8);

        Graphics2D g = (Graphics2D) g2d.create();
        g.setTransform(tx);
        g.fill(arrowHead);
        g.dispose();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }
}