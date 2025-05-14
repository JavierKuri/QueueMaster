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

            JButton addButton = new JButton("Add Node");
            frame.add(addButton, BorderLayout.SOUTH);

            addButton.addActionListener(new ActionListener() {
                int x = 50;

                @Override
                public void actionPerformed(ActionEvent e) {
                    Node newNode = new Node(x, 100, 1, 1.0, 1.0, 1.0, 1.0);
                    graph.addNode(newNode);
                    nodePanel.repaint();
                    x += 150; 
                }
            });

            frame.setVisible(true);
        });
    }
}
