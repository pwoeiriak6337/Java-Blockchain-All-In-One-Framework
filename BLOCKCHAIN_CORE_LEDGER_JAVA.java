import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BlockchainCoreLedger {
    private final List<Block> chain;
    private static final int DIFFICULTY = 4;

    public BlockchainCoreLedger() {
        this.chain = new ArrayList<>();
        createGenesisBlock();
    }

    private void createGenesisBlock() {
        chain.add(new Block(0, "0", System.currentTimeMillis(), "Genesis Block", mineBlock("Genesis Block")));
    }

    private String mineBlock(String data) {
        String hash = calculateHash(data);
        while (!hash.substring(0, DIFFICULTY).equals(getDifficultyString())) {
            hash = calculateHash(data + System.nanoTime());
        }
        return hash;
    }

    private String calculateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String getDifficultyString() {
        return new String(new char[DIFFICULTY]).replace('\0', '0');
    }

    public void addBlock(String data) {
        Block lastBlock = chain.get(chain.size() - 1);
        String newHash = mineBlock(data);
        chain.add(new Block(chain.size(), lastBlock.getHash(), System.currentTimeMillis(), data, newHash));
    }

    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i - 1);
            if (!current.getHash().equals(calculateHash(current.getData() + current.getTimestamp() + current.getPreviousHash()))) {
                return false;
            }
            if (!current.getPreviousHash().equals(previous.getHash())) {
                return false;
            }
        }
        return true;
    }

    static class Block {
        private final int index;
        private final String previousHash;
        private final long timestamp;
        private final String data;
        private final String hash;

        public Block(int index, String previousHash, long timestamp, String data, String hash) {
            this.index = index;
            this.previousHash = previousHash;
            this.timestamp = timestamp;
            this.data = data;
            this.hash = hash;
        }

        public String getHash() { return hash; }
        public String getPreviousHash() { return previousHash; }
        public String getData() { return data; }
        public long getTimestamp() { return timestamp; }
    }
}
