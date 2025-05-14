import javax.swing.*;
import java.awt.*;

public class NodePanel extends JPanel {
    private Graph graph;

    public NodePanel(Graph graph) {
        this.graph = graph;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Node node : graph.getNodes()) {
            node.drawNode(g);

            // Draw arrow (line) to next node if exists
            if (node.nextNode != null) {
                g.setColor(Color.BLACK);
                g.drawLine(node.xCoord, node.yCoord, node.nextNode.xCoord, node.nextNode.yCoord);

                // Draw lambda and mu on the line (optional)
                String label = String.format("λ=%.2f, μ=%.2f", node.lambdaOut, node.muIn);
                int midX = (node.xCoord + node.nextNode.xCoord) / 2;
                int midY = (node.yCoord + node.nextNode.yCoord) / 2;
                g.drawString(label, midX, midY - 5);
            }
        }
    }
}
