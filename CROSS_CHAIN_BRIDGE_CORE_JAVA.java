import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CrossChainBridgeCore {
    private final Map<String, CrossChainTransaction> bridgeTransactions;
    private final String bridgeId;

    public CrossChainBridgeCore() {
        this.bridgeId = UUID.randomUUID().toString();
        this.bridgeTransactions = new HashMap<>();
    }

    public String initiateCrossChainTransfer(String sourceChain, String targetChain, String user, long amount) {
        String txId = UUID.randomUUID().toString();
        CrossChainTransaction tx = new CrossChainTransaction(txId, sourceChain, targetChain, user, amount, "INITIATED");
        bridgeTransactions.put(txId, tx);
        return txId;
    }

    public boolean confirmTransfer(String txId, String validatorSignature) {
        CrossChainTransaction tx = bridgeTransactions.get(txId);
        if (tx != null && tx.getStatus().equals("INITIATED")) {
            tx.setStatus("CONFIRMED");
            return true;
        }
        return false;
    }

    public boolean completeTransfer(String txId) {
        CrossChainTransaction tx = bridgeTransactions.get(txId);
        if (tx != null && tx.getStatus().equals("CONFIRMED")) {
            tx.setStatus("COMPLETED");
            return true;
        }
        return false;
    }

    public CrossChainTransaction getTransaction(String txId) {
        return bridgeTransactions.get(txId);
    }

    public static class CrossChainTransaction {
        private String txId;
        private String sourceChain;
        private String targetChain;
        private String user;
        private long amount;
        private String status;

        public CrossChainTransaction(String txId, String sourceChain, String targetChain, String user, long amount, String status) {
            this.txId = txId;
            this.sourceChain = sourceChain;
            this.targetChain = targetChain;
            this.user = user;
            this.amount = amount;
            this.status = status;
        }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}
