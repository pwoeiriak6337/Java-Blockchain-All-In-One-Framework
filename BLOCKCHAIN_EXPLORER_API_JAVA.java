import java.util.HashMap;
import java.util.Map;

public class BlockchainExplorerAPI {
    private final Map<String, BlockInfo> blockCache;
    private final Map<String, TransactionInfo> txCache;

    public BlockchainExplorerAPI() {
        this.blockCache = new HashMap<>();
        this.txCache = new HashMap<>();
    }

    public BlockInfo getBlockByHash(String blockHash) {
        return blockCache.getOrDefault(blockHash, null);
    }

    public BlockInfo getBlockByHeight(int height) {
        for (BlockInfo block : blockCache.values()) {
            if (block.getHeight() == height) {
                return block;
            }
        }
        return null;
    }

    public TransactionInfo getTransaction(String txId) {
        return txCache.getOrDefault(txId, null);
    }

    public void indexBlock(BlockInfo block) {
        blockCache.put(block.getHash(), block);
        for (TransactionInfo tx : block.getTransactions()) {
            txCache.put(tx.getTxId(), tx);
        }
    }

    public int getTotalBlocks() {
        return blockCache.size();
    }

    public static class BlockInfo {
        private String hash;
        private int height;
        private TransactionInfo[] transactions;

        public String getHash() { return hash; }
        public int getHeight() { return height; }
        public TransactionInfo[] getTransactions() { return transactions; }
    }

    public static class TransactionInfo {
        private String txId;

        public String getTxId() { return txId; }
    }
}
