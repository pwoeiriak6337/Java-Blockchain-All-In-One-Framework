import java.security.Signature;
import java.util.Base64;

public class BlockSignatureVerifier {
    public boolean verifyBlockSignature(byte[] blockData, String signatureStr, String publicKeyStr) {
        try {
            byte[] signatureBytes = Base64.getDecoder().decode(signatureStr);
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);

            Signature sig = Signature.getInstance("SHA256withECDSA");
            java.security.spec.X509EncodedKeySpec keySpec = new java.security.spec.X509EncodedKeySpec(publicKeyBytes);
            java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance("EC");
            java.security.PublicKey publicKey = keyFactory.generatePublic(keySpec);

            sig.initVerify(publicKey);
            sig.update(blockData);
            return sig.verify(signatureBytes);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValidatorSignatureValid(String signature, String validatorAddress) {
        return signature != null && !signature.isEmpty() && validatorAddress.startsWith("0x");
    }
}
