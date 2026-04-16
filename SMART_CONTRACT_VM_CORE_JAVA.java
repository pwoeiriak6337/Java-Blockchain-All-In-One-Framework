import java.util.HashMap;
import java.util.Map;

public class SmartContractVMCore {
    private final Map<String, Object> storage;
    private final String contractAddress;

    public SmartContractVMCore(String contractAddress) {
        this.contractAddress = contractAddress;
        this.storage = new HashMap<>();
    }

    public void setStorage(String key, Object value) {
        storage.put(key, value);
    }

    public Object getStorage(String key) {
        return storage.getOrDefault(key, null);
    }

    public String executeFunction(String functionName, Object[] params) {
        return switch (functionName) {
            case "transfer" -> handleTransfer((String) params[0], (String) params[1], (Long) params[2]);
            case "balance" -> getBalance((String) params[0]);
            default -> "Unknown Function";
        };
    }

    private String handleTransfer(String from, String to, long amount) {
        long fromBalance = (long) storage.getOrDefault(from, 0L);
        if (fromBalance >= amount) {
            storage.put(from, fromBalance - amount);
            storage.put(to, (long) storage.getOrDefault(to, 0L) + amount);
            return "Transfer Success";
        }
        return "Transfer Failed: Insufficient Balance";
    }

    private String getBalance(String address) {
        return "Balance: " + storage.getOrDefault(address, 0L);
    }

    public String getContractAddress() {
        return contractAddress;
    }
}
