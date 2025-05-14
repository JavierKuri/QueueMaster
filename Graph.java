import java.util.ArrayList;

public class Graph {
    private ArrayList<Node> nodes;
    private String queueType = "Undefined";

    public Graph() {
        this.nodes = new ArrayList<>();
    }

    public void addNode(Node node) {
        if (!nodes.isEmpty()) {
            // Link nodes
            Node last = nodes.get(nodes.size() - 1);
            last.nextNode = node;
            node.prevNode = last;
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