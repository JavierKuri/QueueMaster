import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Queue Master");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        Graph graph = new Graph();
        ArrayList<Node> nodes = graph.getNodes();

        //Center panel for displaying the graph
        NodePanel nodePanel = new NodePanel(graph);
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(nodePanel, BorderLayout.CENTER);

        //Result panel for displaying probabilities and other values
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        JTextField probsResultField = new JTextField();
        probsResultField.setEditable(false);
        probsResultField.setPreferredSize(new Dimension(700, 30));
        resultPanel.add(new JLabel("Probabilities: "));
        resultPanel.add(probsResultField);

        JTextField valuesResultField = new JTextField();
        valuesResultField.setEditable(false);
        valuesResultField.setPreferredSize(new Dimension(700, 30));
        resultPanel.add(new JLabel("Values (rho, L, Lq, W, Wq): "));
        resultPanel.add(valuesResultField);

        centerPanel.add(resultPanel, BorderLayout.SOUTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        //Input panel for assigning mu, lambda and s values
        JTextField lambdaInField = new JTextField(5);
        JTextField muOutField = new JTextField(5);
        JTextField sField = new JTextField(5);
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel lambdaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        lambdaPanel.add(new JLabel("λ_in:"));
        lambdaPanel.add(lambdaInField);
        JPanel muPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        muPanel.add(new JLabel("μ_out:"));
        muPanel.add(muOutField);
        JPanel sPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        sPanel.add(new JLabel("s:"));
        sPanel.add(sField);
        inputPanel.add(lambdaPanel);
        inputPanel.add(muPanel);
        inputPanel.add(sPanel);
        frame.add(inputPanel, BorderLayout.NORTH);

        //Bottom panel for buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        JButton addButton = new JButton("Add Node");
        JButton setSButton = new JButton("Set s");
        JButton probsButton = new JButton("Calculate probabilities");
        JButton valuesButton = new JButton("Calculate queue values (rho, L, Lq, W, Wq)");
        JButton clearButton = new JButton("Clear");
        JButton saveButton = new JButton("Save graph");
        bottomPanel.add(addButton);
        bottomPanel.add(setSButton);
        bottomPanel.add(probsButton);
        bottomPanel.add(valuesButton);
        bottomPanel.add(clearButton);
        bottomPanel.add(saveButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        //Create initial node 
        Node newNode = new Node(50, 100, 0, 0.0, 0.0 ,0.0 ,0.0);
        graph.addNode(newNode);

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

                Node newNode = new Node(nodes.get(nodes.size() -1).getx()+150, 100, nodes.get(nodes.size() -1).getn()+1, lambdaIn, muOut, 0.0, 0.0);
                graph.addNode(newNode);
                nodePanel.repaint();
                lambdaInField.setText(null);
                muOutField.setText(null);
            }
        });

        //Button for setting value of s
        setSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int s = 1;
                try {
                    s = Integer.parseInt(sField.getText());
                } catch (NumberFormatException ignored) {}
                graph.setS(s);
                setSButton.setEnabled(false);
                sField.setEditable(false);
            }
        });

        //Button for calculating and displaying probabilities
        probsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = new String();
                ArrayList<Double> probs = graph.getProbabilities();
                for (int i = 0; i < probs.size(); i++) {
                    result += "P" + i + " = " + String.format("%.2f", probs.get(i)) + ", ";
                }
                probsResultField.setText(result);
            }
        });
        
        //Button for calculating and displaying queue values (rho, L, Lq, W, Wq)
        valuesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double rho = graph.calculateRho();
                double L = graph.calculateL();
                double Lq = graph.calculateLq();
                double W = graph.calculateW();
                double Wq = graph.calculateWq();
                String result = new String();
                result = "Rho: " + rho + ", L: " +  L + ", Lq: " +  Lq + ", W: " +  W + ", Wq: " +  Wq;
                valuesResultField.setText(result);
            }
        });


        //Button for clearing graph
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph.clearGraph();
                Node newNode = new Node(50, 100, 0, 0.0, 0.0 ,0.0 ,0.0);
                graph.addNode(newNode);
                nodePanel.repaint();
                probsResultField.setText(null);
                valuesResultField.setText(null);
                sField.setEditable(true);
                setSButton.setEnabled(true);
            }
        });

        //Button for saving graph into JSON
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Node> nodes=graph.getNodes();
                ArrayList<Double> probs = graph.getProbabilities();
                JSONObject obj = new JSONObject();
                obj.put("lambda_test", graph.calculateLambdaTest());
                obj.put("mu_test", graph.calculateMuTest());
                obj.put("s", graph.getS());
                obj.put("l", graph.calculateL());
                obj.put("lq", graph.calculateLq());
                obj.put("w", graph.calculateW());
                obj.put("wq", graph.calculateWq());
                JSONArray json_nodes = new JSONArray();
                for(int i=0;i<nodes.size();i++) {
                    JSONObject nodeObj = new JSONObject();
                    nodeObj.put("n", i);
                    nodeObj.put("probability", probs.get(i));
                    nodeObj.put("lambda_out", nodes.get(i).getLambdaOut());
                    nodeObj.put("mu_in", nodes.get(i).getMuIn());
                    json_nodes.put(nodeObj);
                }
                obj.put("nodes", json_nodes);
                String projectDir = System.getProperty("user.dir");
                File outputFile = new File(projectDir + File.separator + "graph_files" + File.separator + "output.json");
                outputFile.getParentFile().mkdirs();
                try (FileWriter writer = new FileWriter(outputFile)) {
                    writer.write(obj.toString(2)); 
                    System.out.println("JSON saved to: " + outputFile.getAbsolutePath());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
        frame.setVisible(true);
    }
}
