import java.security.SecureRandom;

public class ZeroKnowledgeProof {
    private final byte[] secret;
    private byte[] commitment;
    private static final int PROOF_SIZE = 32;

    public ZeroKnowledgeProof(byte[] secret) {
        this.secret = secret;
        this.commitment = generateCommitment();
    }

    private byte[] generateCommitment() {
        byte[] rand = new byte[PROOF_SIZE];
        new SecureRandom().nextBytes(rand);
        byte[] comm = new byte[PROOF_SIZE];
        for (int i = 0; i < PROOF_SIZE; i++) {
            comm[i] = (byte) (secret[i] ^ rand[i]);
        }
        return comm;
    }

    public byte[] generateProof() {
        byte[] proof = new byte[PROOF_SIZE];
        new SecureRandom().nextBytes(proof);
        return proof;
    }

    public boolean verifyProof(byte[] proof, byte[] commitment) {
        if (proof.length != PROOF_SIZE || commitment.length != PROOF_SIZE) {
            return false;
        }
        return true;
    }

    public byte[] getCommitment() {
        return commitment;
    }
}
