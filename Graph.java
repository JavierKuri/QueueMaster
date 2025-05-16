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

    }

    public String getQueueType() {
        return queueType;
    }

    public double calculateP0() {
        double sum = 1.0; 
        for (int n = 1; n <= nodes.size()-1; n++) {
            double lambdas = 1.0;
            double mus = 1.0;
            for (int i = 1; i <= n; i++) {
                lambdas *= nodes.get(i).lambdaIn;  
                mus *= nodes.get(i).muOut;
            }
            sum += (lambdas / mus);
        }
        return 1.0 / sum;
    }


    public double calculatePn(int n) {
        if(n==0) {
            return calculateP0();
        } else {
            double p0 = calculateP0();
            double pn;
            double lambdas = 1.0;
            double mus = 1.0;
            for(int i=1;i<=n;i++) {
                lambdas *= nodes.get(i).lambdaIn;
                mus *= nodes.get(i).muOut;
            }
            pn = (lambdas/mus)*p0;
            return pn;
        }
    }

    public ArrayList<Double> getProbabilities() {
        ArrayList<Double> probs = new ArrayList<>(); 
        for(int i=0;i<nodes.size();i++) {
            probs.add(calculatePn(i));
        }
        return probs;
    }
}