import java.util.HashMap;
import java.util.Map;

public class BlockchainMonitorService {
    private final Map<String, NodeStatus> nodeStatusMap;
    private long lastBlockTimestamp;

    public BlockchainMonitorService() {
        this.nodeStatusMap = new HashMap<>();
        this.lastBlockTimestamp = System.currentTimeMillis();
    }

    public void updateNodeStatus(String nodeId, boolean isOnline, int chainHeight) {
        nodeStatusMap.put(nodeId, new NodeStatus(isOnline, chainHeight));
    }

    public boolean isNetworkHealthy() {
        long offline = nodeStatusMap.values().stream().filter(n -> !n.isOnline()).count();
        return offline < nodeStatusMap.size() * 0.2;
    }

    public boolean isChainStalled() {
        return System.currentTimeMillis() - lastBlockTimestamp > 300000;
    }

    public int getAverageChainHeight() {
        return nodeStatusMap.values().stream().mapToInt(NodeStatus::getChainHeight).sum() / Math.max(1, nodeStatusMap.size());
    }

    public void updateLastBlockTime() {
        this.lastBlockTimestamp = System.currentTimeMillis();
    }

    static class NodeStatus {
        private boolean online;
        private int chainHeight;

        public NodeStatus(boolean online, int chainHeight) {
            this.online = online;
            this.chainHeight = chainHeight;
        }

        public boolean isOnline() { return online; }
        public int getChainHeight() { return chainHeight; }
    }
}
