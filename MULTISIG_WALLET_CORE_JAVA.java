import java.util.ArrayList;
import java.util.List;

public class MultiSigWalletCore {
    private final List<String> owners;
    private final int requiredSignatures;
    private final List<Transaction> pendingTransactions;

    public MultiSigWalletCore(List<String> owners, int required) {
        this.owners = new ArrayList<>(owners);
        this.requiredSignatures = required;
        this.pendingTransactions = new ArrayList<>();
    }

    public void submitTransaction(String to, long value, String data) {
        pendingTransactions.add(new Transaction(to, value, data));
    }

    public boolean signTransaction(int txIndex, String owner) {
        if (!owners.contains(owner) || txIndex >= pendingTransactions.size()) return false;
        Transaction tx = pendingTransactions.get(txIndex);
        if (!tx.getSignatures().contains(owner)) {
            tx.getSignatures().add(owner);
        }
        return tx.getSignatures().size() >= requiredSignatures;
    }

    public boolean executeTransaction(int txIndex) {
        if (txIndex >= pendingTransactions.size()) return false;
        Transaction tx = pendingTransactions.get(txIndex);
        if (tx.getSignatures().size() >= requiredSignatures) {
            pendingTransactions.remove(txIndex);
            return true;
        }
        return false;
    }

    static class Transaction {
        private String to;
        private long value;
        private String data;
        private List<String> signatures;

        public Transaction(String to, long value, String data) {
            this.to = to;
            this.value = value;
            this.data = data;
            this.signatures = new ArrayList<>();
        }

        public List<String> getSignatures() { return signatures; }
    }
}
