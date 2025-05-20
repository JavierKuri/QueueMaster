import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;

public class Main {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
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

            //Top panel for save and load buttons
            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JButton saveButton = new JButton("Save");
            JButton loadButton = new JButton("Load");
            topPanel.add(saveButton);
            topPanel.add(loadButton);
            topPanel.add(Box.createVerticalStrut(40));

            //Input panel for lambda, mu, s, and file fields
            JPanel inputFieldsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JTextField lambdaInField = new JTextField(5);
            JTextField muOutField = new JTextField(5);
            JTextField sField = new JTextField(5);
            JTextField nameField = new JTextField(10);
            nameField.setText("output.json");
            inputFieldsPanel.add(new JLabel("λ_in:"));
            inputFieldsPanel.add(lambdaInField);
            inputFieldsPanel.add(new JLabel("μ_out:"));
            inputFieldsPanel.add(muOutField);
            inputFieldsPanel.add(new JLabel("s:"));
            inputFieldsPanel.add(sField);
            inputFieldsPanel.add(new JLabel("File name:"));
            inputFieldsPanel.add(nameField);
            inputFieldsPanel.add(Box.createVerticalStrut(40));

            //Button panel for the set s button, add node button, and set file name button
            JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JButton addButton = new JButton("Add Node");
            JButton setSButton = new JButton("Set s");
            buttonsPanel.add(addButton);
            buttonsPanel.add(setSButton);
            buttonsPanel.add(Box.createVerticalStrut(40));

            // Main panel using vertical BoxLayout
            JPanel northPanel = new JPanel();
            northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
            northPanel.add(topPanel);
            northPanel.add(inputFieldsPanel);
            northPanel.add(buttonsPanel);
            frame.add(northPanel, BorderLayout.NORTH);

            //Bottom panel for action buttons
            JPanel bottomPanel = new JPanel();
            bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
            JButton probsButton = new JButton("Calculate probabilities");
            JButton valuesButton = new JButton("Calculate queue values (rho, L, Lq, W, Wq)");
            JButton clearButton = new JButton("Clear");
            bottomPanel.add(probsButton);
            bottomPanel.add(valuesButton);
            bottomPanel.add(clearButton);
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
                        result += "P" + i + " = " + String.format("%.2f", probs.get(i)) + "  ";
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
                    sField.setText(null);
                    nameField.setText("output.json");
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
                    File outputFile = new File(projectDir + File.separator + "graph_files" + File.separator + nameField.getText());
                    outputFile.getParentFile().mkdirs();
                    try (FileWriter writer = new FileWriter(outputFile)) {
                        writer.write(obj.toString(2)); 
                        System.out.println("JSON saved to: " + outputFile.getAbsolutePath());
                        JOptionPane.showMessageDialog(frame, "File saved successfully:\n" + outputFile.getAbsolutePath());
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            });

            //Button for loading graphs from JSON
            loadButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Select a JSON file");
                    int result = fileChooser.showOpenDialog(frame);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        try (FileReader reader = new FileReader(selectedFile)) {
                            JSONTokener tokener = new JSONTokener(reader);
                            JSONObject graphObject = new JSONObject(tokener);
                            JSONArray nodesArray = graphObject.getJSONArray("nodes");
                            graph.clearGraph();
                            Node initialNode = new Node(50, 100, 0, 0.0, 0.0 ,0.0 ,0.0);
                            graph.addNode(initialNode);
                            nodePanel.repaint();
                            for(int i=0;i<nodesArray.length()-1;i++) {
                                JSONObject nodeObj = nodesArray.getJSONObject(i);
                                double lambdaIn = nodeObj.getDouble("lambda_out");
                                double muOut = nodeObj.getDouble("mu_in");
                                Node newNode = new Node(nodes.get(nodes.size() -1).getx()+150, 100, nodes.get(nodes.size() -1).getn()+1, lambdaIn, muOut, 0.0, 0.0);
                                graph.addNode(newNode);
                                nodePanel.repaint();
                                lambdaInField.setText(null);
                                muOutField.setText(null);
                            }
                        } catch (IOException | org.json.JSONException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                        }
                    }
                }
            });
            frame.setVisible(true);
        });
    }
}
