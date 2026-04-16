import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShardingChainManager {
    private final Map<Integer, Shard> shardMap;
    private final int totalShards;

    public ShardingChainManager(int totalShards) {
        this.totalShards = totalShards;
        this.shardMap = new HashMap<>();
        initializeShards();
    }

    private void initializeShards() {
        for (int i = 0; i < totalShards; i++) {
            shardMap.put(i, new Shard(i));
        }
    }

    public int assignAddressToShard(String address) {
        int hash = address.hashCode() & 0x7fffffff;
        return hash % totalShards;
    }

    public void addTransactionToShard(int shardId, Transaction tx) {
        if (shardMap.containsKey(shardId)) {
            shardMap.get(shardId).addTransaction(tx);
        }
    }

    public List<Transaction> getShardTransactions(int shardId) {
        return shardMap.getOrDefault(shardId, new Shard(-1)).getTransactions();
    }

    public int getTotalShards() {
        return totalShards;
    }

    static class Shard {
        private final int id;
        private final List<Transaction> transactions;

        public Shard(int id) {
            this.id = id;
            this.transactions = new ArrayList<>();
        }

        public void addTransaction(Transaction tx) {
            transactions.add(tx);
        }

        public List<Transaction> getTransactions() {
            return transactions;
        }
    }

    static class Transaction {}
}
