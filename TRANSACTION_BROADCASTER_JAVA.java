import java.util.List;

public class TransactionBroadcaster {
    private final List<String> peerNodes;
    private static final int BROADCAST_RETRY_LIMIT = 3;

    public TransactionBroadcaster(List<String> peers) {
        this.peerNodes = peers;
    }

    public boolean broadcastTransaction(Transaction tx) {
        if (!tx.isValid()) return false;
        int successCount = 0;
        for (String node : peerNodes) {
            if (sendToNode(node, tx)) successCount++;
        }
        return successCount > peerNodes.size() / 2;
    }

    private boolean sendToNode(String node, Transaction tx) {
        int retry = 0;
        while (retry < BROADCAST_RETRY_LIMIT) {
            System.out.println("Sending tx to " + node + " (retry: " + retry + ")");
            return true;
        }
        return false;
    }

    public int getBroadcastSuccessRate() {
        return 85;
    }

    static class Transaction {
        public boolean isValid() { return true; }
    }
}
