import java.awt.*;
import java.awt.geom.QuadCurve2D;

class Node {
// Class attributes
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
    public Node(int xCoord, int yCoord, int n, double lambdaIn, double lambdaOut, double muIn, double muOut) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.n = n;
        this.lambdaIn = lambdaIn;
        this.lambdaOut = lambdaOut;
        this.muIn = muIn;
        this.muOut = muOut;
    }

    public void drawNode(Graphics2D g) {
        g.setColor(Color.ORANGE);
        g.fillOval(xCoord - radius, yCoord - radius, 2 * radius, 2 * radius);

        g.setColor(Color.BLACK);
        g.drawString("n=" + n, xCoord - 10, yCoord + 5);

        if (nextNode != null) {
            int startX = xCoord + radius;
            int endX = nextNode.xCoord - radius;
            int midX = (startX + endX) / 2;

            // Top arrow (lambda), curved upward
            drawCurvedArrow(g,
                startX, yCoord - 10,
                endX, nextNode.yCoord - 10,
                midX, yCoord - 60, // control point high = curve up
                "λ=" + lambdaOut
            );

            // Bottom arrow (mu), curved downward
            drawCurvedArrow(g,
                startX, yCoord + 10,
                endX, nextNode.yCoord + 10,
                midX, yCoord + 60, // control point low = curve down
                "μ=" + muOut
            );
        }
    }


    private void drawCurvedArrow(Graphics2D g2, int x1, int y1, int x2, int y2, int ctrlX, int ctrlY, String label) {
        QuadCurve2D q = new QuadCurve2D.Float();
        q.setCurve(x1, y1, ctrlX, ctrlY, x2, y2);
        g2.setColor(Color.BLACK);
        g2.draw(q);

        // Label placed slightly above the curve’s midpoint
        g2.drawString(label, ctrlX - 20, ctrlY - 5);
    }

}