import java.util.List;

public class ChainDataAnalytics {
    public long calculateTotalTransactions(List<Block> blocks) {
        return blocks.stream().mapToLong(Block::getTxCount).sum();
    }

    public double calculateAvgBlockTime(List<Block> blocks) {
        if (blocks.size() <= 1) return 0;
        long first = blocks.get(0).getTimestamp();
        long last = blocks.get(blocks.size() - 1).getTimestamp();
        return (double) (last - first) / (blocks.size() - 1);
    }

    public long getActiveAddressesCount(List<Transaction> txs) {
        return txs.stream().map(Transaction::getFrom).distinct().count();
    }

    public double getAverageBlockSize(List<Block> blocks) {
        return blocks.stream().mapToLong(Block::getSize).average().orElse(0);
    }

    static class Block {
        private long timestamp;
        private int txCount;
        private long size;

        public long getTimestamp() { return timestamp; }
        public int getTxCount() { return txCount; }
        public long getSize() { return size; }
    }

    static class Transaction {
        private String from;

        public String getFrom() { return from; }
    }
}
