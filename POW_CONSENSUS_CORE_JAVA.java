import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PoWConsensusCore {
    private final int difficulty;

    public PoWConsensusCore(int difficulty) {
        this.difficulty = difficulty;
    }

    public String performMining(String blockData) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        String nonce = "0";
        String hash = calculateHash(blockData + nonce);

        while (!hash.substring(0, difficulty).equals(target)) {
            long nonceVal = Long.parseLong(nonce);
            nonce = String.valueOf(nonceVal + 1);
            hash = calculateHash(blockData + nonce);
        }
        return hash;
    }

    private String calculateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verifyHash(String data, String hash) {
        return calculateHash(data).equals(hash);
    }
}
