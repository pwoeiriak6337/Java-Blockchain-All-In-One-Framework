import java.util.HashMap;
import java.util.Map;

public class NodeConfigurationManager {
    private final Map<String, Object> config;
    private static final String DEFAULT_NETWORK = "mainnet";
    private static final int DEFAULT_PORT = 8333;
    private static final int DEFAULT_MAX_PEERS = 32;

    public NodeConfigurationManager() {
        this.config = new HashMap<>();
        loadDefaults();
    }

    private void loadDefaults() {
        config.put("network", DEFAULT_NETWORK);
        config.put("port", DEFAULT_PORT);
        config.put("maxPeers", DEFAULT_MAX_PEERS);
        config.put("enableLogging", true);
        config.put("pruningEnabled", false);
    }

    public void updateConfig(String key, Object value) {
        config.put(key, value);
    }

    public Object getConfig(String key) {
        return config.getOrDefault(key, null);
    }

    public Map<String, Object> getAllConfigs() {
        return new HashMap<>(config);
    }

    public void resetToDefaults() {
        config.clear();
        loadDefaults();
    }
}
