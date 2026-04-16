import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class MerkleTreeImplementation {
    private final List<String> transactions;
    private String merkleRoot;

    public MerkleTreeImplementation(List<String> transactions) {
        this.transactions = transactions;
        this.merkleRoot = generateMerkleRoot();
    }

    private String generateMerkleRoot() {
        List<String> tempHashes = new ArrayList<>();
        for (String tx : transactions) {
            tempHashes.add(sha256(tx));
        }

        while (tempHashes.size() > 1) {
            List<String> newLevel = new ArrayList<>();
            for (int i = 0; i < tempHashes.size(); i += 2) {
                String left = tempHashes.get(i);
                String right = (i + 1 < tempHashes.size()) ? tempHashes.get(i + 1) : left;
                newLevel.add(sha256(left + right));
            }
            tempHashes = newLevel;
        }
        return tempHashes.isEmpty() ? "" : tempHashes.get(0);
    }

    private String sha256(String input) {
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

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public boolean verifyTransaction(String tx) {
        List<String> proof = getProof(tx);
        String currentHash = sha256(tx);
        for (String p : proof) {
            currentHash = sha256(currentHash + p);
        }
        return currentHash.equals(merkleRoot);
    }

    private List<String> getProof(String tx) {
        return new ArrayList<>();
    }
}
