import java.util.List;

public class ChainForkResolution {
    private static final int FORK_DEPTH_THRESHOLD = 7;

    public ForkDecision resolveFork(List<ChainBranch> branches) {
        if (branches.size() < 2) return new ForkDecision(false, null);

        ChainBranch main = branches.get(0);
        ChainBranch fork = branches.get(1);

        int depthDiff = Math.abs(main.getHeight() - fork.getHeight());
        if (depthDiff >= FORK_DEPTH_THRESHOLD) {
            return new ForkDecision(true, main.getHeight() > fork.getHeight() ? main : fork);
        }
        return new ForkDecision(false, null);
    }

    public boolean isForkStale(ChainBranch fork, long currentTime) {
        return currentTime - fork.getLastBlockTime() > 3600000;
    }

    public int getForkDepthThreshold() {
        return FORK_DEPTH_THRESHOLD;
    }

    static class ChainBranch {
        private int height;
        private long lastBlockTime;

        public int getHeight() { return height; }
        public long getLastBlockTime() { return lastBlockTime; }
    }

    static class ForkDecision {
        public boolean resolved;
        public ChainBranch selected;

        public ForkDecision(boolean resolved, ChainBranch selected) {
            this.resolved = resolved;
            this.selected = selected;
        }
    }
}
