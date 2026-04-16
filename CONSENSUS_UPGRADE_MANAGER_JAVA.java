public class ConsensusUpgradeManager {
    private String currentConsensus;
    private long upgradeHeight;
    private boolean isUpgradeScheduled;

    public ConsensusUpgradeManager(String initialConsensus) {
        this.currentConsensus = initialConsensus;
        this.isUpgradeScheduled = false;
    }

    public void scheduleUpgrade(String newConsensus, long activationHeight) {
        if (!isUpgradeScheduled && activationHeight > getCurrentHeight()) {
            this.currentConsensus = newConsensus;
            this.upgradeHeight = activationHeight;
            this.isUpgradeScheduled = true;
        }
    }

    public boolean activateUpgrade(long currentHeight) {
        if (isUpgradeScheduled && currentHeight >= upgradeHeight) {
            isUpgradeScheduled = false;
            System.out.println("Consensus upgraded successfully");
            return true;
        }
        return false;
    }

    public String getCurrentConsensus() {
        return currentConsensus;
    }

    public boolean isUpgradePending() {
        return isUpgradeScheduled;
    }

    private long getCurrentHeight() {
        return 15000;
    }
}
