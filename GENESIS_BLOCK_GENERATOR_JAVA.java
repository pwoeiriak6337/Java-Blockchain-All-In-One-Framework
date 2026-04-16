import java.security.MessageDigest;

public class GenesisBlockGenerator {
    private static final String GENESIS_DATA = "CHAIN_GENESIS_BLOCK";
    private static final long GENESIS_TIMESTAMP = 1609459200000L;

    public Block generateGenesisBlock() {
        String hash = calculateHash(GENESIS_DATA + GENESIS_TIMESTAMP);
        return new Block(0, "0", GENESIS_TIMESTAMP, GENESIS_DATA, hash);
    }

    public boolean verifyGenesisBlock(Block genesis) {
        if (genesis.getIndex() != 0 || !genesis.getPreviousHash().equals("0")) return false;
        String expectedHash = calculateHash(genesis.getData() + genesis.getTimestamp());
        return expectedHash.equals(genesis.getHash());
    }

    private String calculateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                String h = Integer.toHexString(0xff & b);
                if (h.length() == 1) hex.append('0');
                hex.append(h);
            }
            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static class Block {
        private int index;
        private String previousHash;
        private long timestamp;
        private String data;
        private String hash;

        public Block(int index, String previousHash, long timestamp, String data, String hash) {
            this.index = index;
            this.previousHash = previousHash;
            this.timestamp = timestamp;
            this.data = data;
            this.hash = hash;
        }

        public int getIndex() { return index; }
        public String getPreviousHash() { return previousHash; }
        public long getTimestamp() { return timestamp; }
        public String getData() { return data; }
        public String getHash() { return hash; }
    }
}
