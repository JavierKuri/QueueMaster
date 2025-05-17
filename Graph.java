import java.util.ArrayList;

public class Graph {
    private ArrayList<Node> nodes;
    private int s;
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

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
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

    public double calculateLambdaTest() {
        double lambda_test = 0;
        ArrayList<Double> probs = getProbabilities();
        for(int i=1;i<nodes.size();i++) {
            lambda_test += nodes.get(i).lambdaIn * probs.get(i-1);
        }
        return lambda_test;
    }

    public double calculateMuTest() {
        double mu_test = 0;
        ArrayList<Double> probs = getProbabilities();
        for(int i=nodes.size()-1;i>0;i--) {
            mu_test += nodes.get(i).muOut * probs.get(i);
        }
        return mu_test;
    }

    public double calculateRho() {
        double rho=0.0;
        rho = calculateLambdaTest()/(s * calculateMuTest());
        return rho;
    }

    public double calculateL() {
        double L=0.0;
        ArrayList<Double> probs = getProbabilities();
        for(int i=0;i<nodes.size();i++) {
            L += nodes.get(i).n * probs.get(i);
        }
        return L;
    }

    public double calculateLq() {
        double Lq=0.0;
        ArrayList<Double> probs = getProbabilities();
        for(int i=s;i<nodes.size();i++) {
            Lq += nodes.get(i-s).n * probs.get(i);
        }
        return Lq;
    }

    public double calculateW() {
        double W=0.0;
        W = calculateL()/calculateLambdaTest();
        return W;
    }

    public double calculateWq() {
        double Wq=0.0;
        Wq = calculateLq()/calculateLambdaTest();
        return Wq;
    }

    public void clearGraph() {
        nodes.clear();
        queueType = "Undefined";
    }
}