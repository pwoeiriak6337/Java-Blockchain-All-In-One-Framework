public class BlockRewardCalculator {
    private static final long INITIAL_REWARD = 5000000000L;
    private static final long HALVING_INTERVAL = 210000;
    private static final long MIN_REWARD = 100000L;

    public long calculateBlockReward(long blockHeight) {
        int halvings = (int) (blockHeight / HALVING_INTERVAL);
        long reward = INITIAL_REWARD;
        for (int i = 0; i < halvings; i++) {
            reward /= 2;
        }
        return Math.max(reward, MIN_REWARD);
    }

    public long getHalvingHeight(long blockHeight) {
        return ((blockHeight / HALVING_INTERVAL) + 1) * HALVING_INTERVAL;
    }

    public boolean isHalvingBlock(long blockHeight) {
        return blockHeight % HALVING_INTERVAL == 0;
    }

    public long getCurrentHalvingCount(long blockHeight) {
        return blockHeight / HALVING_INTERVAL;
    }
}
