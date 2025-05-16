import java.awt.*;
import java.awt.geom.QuadCurve2D;

class Node {
    // Queueing attributes
    int n;
    double lambdaIn;
    double lambdaOut;
    double muIn;
    double muOut;
    Node prevNode;
    Node nextNode;

    // Graphics attributes
    int xCoord, yCoord;
    int radius = 40;

    // Constructor
    public Node(int xCoord, int yCoord, int n, double lambdaIn, double muOut, double lambdaOut, double muIn) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.n = n;
        this.lambdaIn = lambdaIn;
        this.muOut = muOut;
        this.lambdaOut = lambdaOut;
        this.muIn = muIn;
    }

    //Getters and Setters
    public void setLambdaIn(double lambdaIn) {
        this.lambdaIn = lambdaIn;
    }

    public void setMuOut(double muOut) {
        this.muOut = muOut;
    }

    public double getLambdaOut() {
        return lambdaOut;
    }

    public double getMuIn() {
        return muIn;
    }

    //Method for drawing the node
    public void drawNode(Graphics2D g) {
        g.setColor(Color.ORANGE);
        g.fillOval(xCoord - radius, yCoord - radius, 2 * radius, 2 * radius);

        g.setColor(Color.BLACK);
        g.drawOval(xCoord - radius, yCoord - radius, 2 * radius, 2 * radius);
        g.drawString("n=" + n, xCoord - 10, yCoord + 5);

        // Draw λ and μ only if this node connects to next
        if (nextNode != null) {
            int startX = xCoord + radius;
            int endX = nextNode.xCoord - radius;
            int midX = (startX + endX) / 2;

            // Top arrow (λ), curved upward
            drawCurvedArrow(g,
                startX, yCoord - 10,
                endX, nextNode.yCoord - 10,
                midX, yCoord - 60,
                "λ" + n + "=" + lambdaOut
            );

            // Bottom arrow (μ), curved downward
            drawCurvedArrow(g,
                startX, yCoord + 10,
                endX, nextNode.yCoord + 10,
                midX, yCoord + 60,
                "μ" + (n+1) +"=" + muIn
            );
        }
    }

    private void drawCurvedArrow(Graphics2D g2, int x1, int y1, int x2, int y2, int ctrlX, int ctrlY, String label) {
        QuadCurve2D q = new QuadCurve2D.Float();
        q.setCurve(x1, y1, ctrlX, ctrlY, x2, y2);
        g2.setColor(Color.BLACK);
        g2.draw(q);

        // Draw label slightly offset
        g2.drawString(label, ctrlX - 20, ctrlY - 5);
    }
}
