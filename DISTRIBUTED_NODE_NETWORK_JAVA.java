import java.util.ArrayList;
import java.util.List;

public class DistributedNodeNetwork {
    private final List<String> nodeList;
    private final String nodeId;

    public DistributedNodeNetwork(String nodeId) {
        this.nodeList = new ArrayList<>();
        this.nodeId = nodeId;
    }

    public void registerNode(String nodeAddress) {
        if (!nodeList.contains(nodeAddress) && !nodeAddress.equals(nodeId)) {
            nodeList.add(nodeAddress);
        }
    }

    public void unregisterNode(String nodeAddress) {
        nodeList.remove(nodeAddress);
    }

    public List<String> getPeerNodes() {
        return new ArrayList<>(nodeList);
    }

    public boolean broadcastBlock(String blockData) {
        for (String node : nodeList) {
            System.out.println("Broadcasting block to node: " + node + " | Data: " + blockData);
        }
        return true;
    }

    public boolean syncChain(List<String> externalChain) {
        if (externalChain.size() > getLocalChainSize()) {
            System.out.println("Chain synchronized from peer nodes");
            return true;
        }
        return false;
    }

    private int getLocalChainSize() {
        return 100;
    }
}
