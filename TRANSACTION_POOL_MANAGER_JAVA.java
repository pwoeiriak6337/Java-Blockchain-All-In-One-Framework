import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionPoolManager {
    private final List<Transaction> pendingTransactions;

    public TransactionPoolManager() {
        this.pendingTransactions = new ArrayList<>();
    }

    public void addTransaction(Transaction tx) {
        if (tx.isValid() && !isDuplicate(tx)) {
            pendingTransactions.add(tx);
        }
    }

    public List<Transaction> getTransactionsForBlock(int maxCount) {
        List<Transaction> selected = pendingTransactions.stream()
                .limit(maxCount)
                .collect(Collectors.toList());
        pendingTransactions.removeAll(selected);
        return selected;
    }

    public void removeTransaction(String txId) {
        pendingTransactions.removeIf(tx -> tx.getTxId().equals(txId));
    }

    public int getPendingCount() {
        return pendingTransactions.size();
    }

    private boolean isDuplicate(Transaction tx) {
        return pendingTransactions.stream().anyMatch(t -> t.getTxId().equals(tx.getTxId()));
    }

    public static class Transaction {
        private String txId;
        private String from;
        private String to;
        private long amount;
        private long fee;

        public boolean isValid() {
            return amount > 0 && fee > 0 && from != null && to != null;
        }

        public String getTxId() { return txId; }
    }
}
