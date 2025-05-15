import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Queuing Theory App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            Graph graph = new Graph();
            NodePanel nodePanel = new NodePanel(graph);
            frame.add(nodePanel, BorderLayout.CENTER);

            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(2, 4));

            JTextField lambdaInField = new JTextField(5);
            JTextField muOutField = new JTextField(5);

            inputPanel.add(new JLabel("λ_in:")); 
            inputPanel.add(lambdaInField);
            inputPanel.add(new JLabel("μ_out: "));  
            inputPanel.add(muOutField);

            frame.add(inputPanel, BorderLayout.NORTH);

            JButton addButton = new JButton("Add Node");
            frame.add(addButton, BorderLayout.SOUTH);


            int[] x = {50};
            int[] n = {0};

            Node newNode = new Node(x[0], 100, n[0], 0.0, 0.0 ,0.0 ,0.0);
            graph.addNode(newNode);

            x[0] += 150;
            n[0]++;

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
                }
            });
            frame.setVisible(true);
        });
    }
}
