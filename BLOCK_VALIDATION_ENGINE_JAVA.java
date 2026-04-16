import java.security.MessageDigest;

public class BlockValidationEngine {
    private static final int DIFFICULTY = 4;

    public boolean validateEntireBlock(BlockData block) {
        if (!validateHashDifficulty(block.getHash())) return false;
        if (!validatePreviousHash(block)) return false;
        if (!validateDataSignature(block)) return false;
        return true;
    }

    private boolean validateHashDifficulty(String hash) {
        return hash.substring(0, DIFFICULTY).equals(getDifficultyPrefix());
    }

    private boolean validatePreviousHash(BlockData block) {
        return block.getPreviousHash() != null && !block.getPreviousHash().isEmpty();
    }

    private boolean validateDataSignature(BlockData block) {
        String computedHash = calculateHash(block.getData() + block.getTimestamp() + block.getPreviousHash());
        return computedHash.equals(block.getHash());
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

    private String getDifficultyPrefix() {
        return new String(new char[DIFFICULTY]).replace('\0', '0');
    }

    public static class BlockData {
        private String hash;
        private String previousHash;
        private long timestamp;
        private String data;

        public String getHash() { return hash; }
        public String getPreviousHash() { return previousHash; }
        public long getTimestamp() { return timestamp; }
        public String getData() { return data; }
    }
}
