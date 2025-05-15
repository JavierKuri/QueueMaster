import java.util.ArrayList;

public class Graph {
    private ArrayList<Node> nodes;
    private String queueType = "Undefined";

    public Graph() {
        this.nodes = new ArrayList<>();
    }

    public void addNode(Node node) {
    if (nodes.isEmpty()) {
        node.lambdaIn = 0.0;
        node.muOut = 0.0;
        node.lambdaOut = 0.0;
        node.muIn = 0.0;
    } else {
        Node prev = nodes.get(nodes.size() - 1);
        prev.nextNode = node;
        node.prevNode = prev;
        prev.lambdaOut = node.lambdaIn;
        prev.muIn = node.muOut;
        node.muIn = 0.0;
        node.lambdaOut = 0.0;
    }

    nodes.add(node);
}
    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void determineQueueType() {
        //TODO
    }

    public String getQueueType() {
        return queueType;
    }
}