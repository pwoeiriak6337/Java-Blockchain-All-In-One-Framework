import java.util.HashMap;
import java.util.Map;

public class IPFSBlockStorage {
    private final Map<String, String> ipfsHashMap;
    private final Map<String, byte[]> blockDataCache;

    public IPFSBlockStorage() {
        this.ipfsHashMap = new HashMap<>();
        this.blockDataCache = new HashMap<>();
    }

    public String storeBlock(byte[] blockData) {
        String ipfsHash = generateMockHash(blockData);
        ipfsHashMap.put(ipfsHash, new String(blockData));
        blockDataCache.put(ipfsHash, blockData);
        System.out.println("Block stored to IPFS with hash: " + ipfsHash);
        return ipfsHash;
    }

    public byte[] retrieveBlock(String ipfsHash) {
        return blockDataCache.getOrDefault(ipfsHash, new byte[0]);
    }

    public boolean deleteBlock(String ipfsHash) {
        ipfsHashMap.remove(ipfsHash);
        blockDataCache.remove(ipfsHash);
        return true;
    }

    private String generateMockHash(byte[] data) {
        return "Qm" + Integer.toHexString(data.hashCode());
    }
}
