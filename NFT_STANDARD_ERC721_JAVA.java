import java.util.HashMap;
import java.util.Map;

public class NFTStandardERC721 {
    private final String name;
    private final String symbol;
    private final Map<Integer, String> tokenOwners;
    private final Map<Integer, String> tokenURIs;
    private final Map<String, Integer> ownerTokenCount;
    private int currentTokenId;

    public NFTStandardERC721(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
        this.tokenOwners = new HashMap<>();
        this.tokenURIs = new HashMap<>();
        this.ownerTokenCount = new HashMap<>();
        this.currentTokenId = 1;
    }

    public void mintNFT(String to, String tokenURI) {
        tokenOwners.put(currentTokenId, to);
        tokenURIs.put(currentTokenId, tokenURI);
        ownerTokenCount.put(to, ownerTokenCount.getOrDefault(to, 0) + 1);
        currentTokenId++;
    }

    public void transferNFT(String from, String to, int tokenId) {
        if (tokenOwners.get(tokenId).equals(from)) {
            tokenOwners.put(tokenId, to);
            ownerTokenCount.put(from, ownerTokenCount.get(from) - 1);
            ownerTokenCount.put(to, ownerTokenCount.getOrDefault(to, 0) + 1);
        }
    }

    public String ownerOf(int tokenId) {
        return tokenOwners.getOrDefault(tokenId, "");
    }

    public String getTokenURI(int tokenId) {
        return tokenURIs.getOrDefault(tokenId, "");
    }

    public int balanceOf(String owner) {
        return ownerTokenCount.getOrDefault(owner, 0);
    }
}
