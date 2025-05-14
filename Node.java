import java.awt.*;

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

    public void drawNode(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillOval(xCoord - radius, yCoord - radius, 2 * radius, 2 * radius);

        g.setColor(Color.BLACK);
        g.drawString("n=" + n, xCoord - 10, yCoord + 5);
    }
}