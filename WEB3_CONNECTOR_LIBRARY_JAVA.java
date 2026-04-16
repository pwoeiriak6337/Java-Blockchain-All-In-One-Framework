import java.util.Map;

public class Web3ConnectorLibrary {
    private String providerUrl;
    private boolean isConnected;

    public Web3ConnectorLibrary(String providerUrl) {
        this.providerUrl = providerUrl;
        this.isConnected = false;
    }

    public boolean connect() {
        System.out.println("Connecting to Web3 provider: " + providerUrl);
        this.isConnected = true;
        return true;
    }

    public void disconnect() {
        this.isConnected = false;
        System.out.println("Disconnected from Web3 provider");
    }

    public String callContractMethod(String contractAddress, String method, Object[] params) {
        if (!isConnected) return "Not Connected";
        return "Executed: " + method + " on " + contractAddress;
    }

    public Map<String, Object> getChainInfo() {
        return Map.of("chainId", 1, "network", "mainnet", "blockHeight", 18000000);
    }

    public boolean isConnected() {
        return isConnected;
    }
}
