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
        Graphics2D g2 = (Graphics2D) g;

        // Enable anti-aliasing for smooth curves
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Node node : graph.getNodes()) {
            node.drawNode(g2);
        }
    }
}
