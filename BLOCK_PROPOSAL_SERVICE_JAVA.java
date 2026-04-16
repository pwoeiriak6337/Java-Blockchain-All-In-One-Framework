import java.util.List;

public class BlockProposalService {
    private final String validatorAddress;
    private long lastProposalTime;
    private static final long PROPOSAL_INTERVAL = 10000;

    public BlockProposalService(String validator) {
        this.validatorAddress = validator;
        this.lastProposalTime = 0;
    }

    public BlockData proposeNewBlock(List<Transaction> transactions, String previousHash) {
        if (System.currentTimeMillis() - lastProposalTime < PROPOSAL_INTERVAL) {
            return null;
        }
        lastProposalTime = System.currentTimeMillis();
        return new BlockData(transactions.size(), previousHash, System.currentTimeMillis(), generateBlockHash(transactions, previousHash));
    }

    private String generateBlockHash(List<Transaction> txs, String prevHash) {
        return Integer.toHexString((txs.toString() + prevHash).hashCode());
    }

    public boolean canPropose() {
        return System.currentTimeMillis() - lastProposalTime >= PROPOSAL_INTERVAL;
    }

    static class Transaction {}

    static class BlockData {
        private int txCount;
        private String previousHash;
        private long timestamp;
        private String hash;

        public BlockData(int txCount, String previousHash, long timestamp, String hash) {
            this.txCount = txCount;
            this.previousHash = previousHash;
            this.timestamp = timestamp;
            this.hash = hash;
        }
    }
}
