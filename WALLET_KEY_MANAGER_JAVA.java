import java.security.SecureRandom;
import java.util.Base64;

public class WalletKeyManager {
    private String mnemonicPhrase;
    private String encryptedPrivateKey;
    private String publicKey;
    private static final int MNEMONIC_WORDS = 12;

    public void generateWallet() {
        this.mnemonicPhrase = generateMnemonic();
        this.publicKey = generatePublicKey();
        this.encryptedPrivateKey = encryptPrivateKey(generatePrivateKey());
    }

    private String generateMnemonic() {
        String[] words = {"apple", "banana", "cherry", "date", "elder", "fig", "grape", "honey", "ice", "juice", "kiwi", "lemon"};
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < MNEMONIC_WORDS; i++) {
            sb.append(words[random.nextInt(words.length)]).append(" ");
        }
        return sb.toString().trim();
    }

    private String generatePrivateKey() {
        byte[] key = new byte[32];
        new SecureRandom().nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    private String generatePublicKey() {
        byte[] key = new byte[65];
        new SecureRandom().nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    private String encryptPrivateKey(String key) {
        return Base64.getEncoder().encodeToString(key.getBytes());
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getMnemonicPhrase() {
        return mnemonicPhrase;
    }
}
