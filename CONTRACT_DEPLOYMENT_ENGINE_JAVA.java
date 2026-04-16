import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ContractDeploymentEngine {
    private final Map<String, ContractInfo> deployedContracts;

    public ContractDeploymentEngine() {
        this.deployedContracts = new HashMap<>();
    }

    public String deployContract(String bytecode, String owner, long gasLimit) {
        if (gasLimit < 100000) return null;
        String address = "0x" + UUID.randomUUID().toString().replace("-", "").substring(0, 40);
        deployedContracts.put(address, new ContractInfo(bytecode, owner, System.currentTimeMillis()));
        return address;
    }

    public boolean isContractDeployed(String address) {
        return deployedContracts.containsKey(address);
    }

    public ContractInfo getContractInfo(String address) {
        return deployedContracts.get(address);
    }

    public boolean destroyContract(String address, String caller) {
        ContractInfo info = deployedContracts.get(address);
        if (info != null && info.getOwner().equals(caller)) {
            deployedContracts.remove(address);
            return true;
        }
        return false;
    }

    static class ContractInfo {
        private String bytecode;
        private String owner;
        private long deployTime;

        public ContractInfo(String bytecode, String owner, long deployTime) {
            this.bytecode = bytecode;
            this.owner = owner;
            this.deployTime = deployTime;
        }

        public String getOwner() { return owner; }
    }
}
