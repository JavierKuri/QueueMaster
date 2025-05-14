import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class NodePanel extends JPanel {
    Graph graph = new Graph();
    ArrayList<Node> nodes = new ArrayList<>();

    public NodePanel() {
        graph.addNode(new Node(100, 100, 1, 2.0, 1.0, 3.0, 1.5));
        graph.addNode(new Node(250, 100, 2, 1.5, 1.2, 2.0, 1.0));
        graph.addNode(new Node(400, 100, 3, 0.8, 0.6, 1.5, 1.0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Node node : graph.getNodes()) {
            node.drawNode(g);

            if (node.nextNode != null) {
                g.setColor(Color.BLACK);
                g.drawLine(node.xCoord, node.yCoord, node.nextNode.xCoord, node.nextNode.yCoord);
            }
        }
    }
}
