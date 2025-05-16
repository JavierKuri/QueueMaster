import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Queuing Theory App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        Graph graph = new Graph();

        //Center panel for displaying the graph
        NodePanel nodePanel = new NodePanel(graph);
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(nodePanel, BorderLayout.CENTER);

        //Result panel for displaying probabilities and other values
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JTextField probsResultField = new JTextField();
        probsResultField.setEditable(false);
        probsResultField.setPreferredSize(new Dimension(700, 30));
        resultPanel.add(new JLabel("Probabilities: "));
        resultPanel.add(probsResultField);
        centerPanel.add(resultPanel, BorderLayout.SOUTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        //Input panel for assigning mu and lambda values
        JTextField lambdaInField = new JTextField(5);
        JTextField muOutField = new JTextField(5);
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel lambdaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        lambdaPanel.add(new JLabel("λ_in:"));
        lambdaPanel.add(lambdaInField);
        JPanel muPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        muPanel.add(new JLabel("μ_out:"));
        muPanel.add(muOutField);
        inputPanel.add(lambdaPanel);
        inputPanel.add(muPanel);
        frame.add(inputPanel, BorderLayout.NORTH);

        //Bottom panel for buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        JButton addButton = new JButton("Add Node");
        JButton probsButton = new JButton("Calculate probabilities");
        bottomPanel.add(addButton);
        bottomPanel.add(probsButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        //Counters for position and node number
        int[] x = {50};
        int[] n = {0};

        //Create initial node and increment counters
        Node newNode = new Node(x[0], 100, n[0], 0.0, 0.0 ,0.0 ,0.0);
        graph.addNode(newNode);
        x[0] += 150;
        n[0]++;

        //Button for adding nodes
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double lambdaIn = 0.0;
                double muOut = 0.0;

                try {
                    lambdaIn = Double.parseDouble(lambdaInField.getText());
                } catch (NumberFormatException ignored) {}

                try {
                    muOut = Double.parseDouble(muOutField.getText());
                } catch (NumberFormatException ignored) {}

                Node newNode = new Node(x[0], 100, n[0], lambdaIn, muOut, 0.0, 0.0);
                graph.addNode(newNode);

                nodePanel.repaint();

                x[0] += 150;
                n[0]++;

                lambdaInField.setText(null);
                muOutField.setText(null);
            }
        });

        //Button for clculating and displaying probabilities
        probsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = new String();

for (Node node : graph.getNodes()) {
    System.out.println("Node " + node.n +
        ": lambda_out = " + node.lambdaOut +
        ", mu_in = " + node.muIn +
        ", lambda_in = " + node.lambdaIn +
        ", mu_out = " + node.muOut);
}


                ArrayList<Double> probs = graph.getProbabilities();
                for (int i = 0; i < probs.size(); i++) {
                    result += "P" + i + " = " + String.format("%.2f", probs.get(i)) + ", ";
                }
                probsResultField.setText(result);
            }
        });

        frame.setVisible(true);
    }
}
