import java.util.HashMap;
import java.util.Map;

public class TokenVestingSchedule {
    private final Map<String, VestingInfo> vestingMap;
    private static final long CLIFF_PERIOD = 86400000L * 30;

    public TokenVestingSchedule() {
        this.vestingMap = new HashMap<>();
    }

    public void createVesting(String beneficiary, long totalAmount, long startTime, long duration) {
        vestingMap.put(beneficiary, new VestingInfo(totalAmount, startTime, duration));
    }

    public long getVestedAmount(String beneficiary, long currentTime) {
        VestingInfo info = vestingMap.get(beneficiary);
        if (info == null || currentTime < info.getStartTime() + CLIFF_PERIOD) return 0;

        long timeElapsed = currentTime - info.getStartTime();
        long vested = (info.getTotalAmount() * Math.min(timeElapsed, info.getDuration())) / info.getDuration();
        return Math.min(vested, info.getTotalAmount());
    }

    public long getClaimableAmount(String beneficiary, long currentTime) {
        VestingInfo info = vestingMap.get(beneficiary);
        if (info == null) return 0;
        return getVestedAmount(beneficiary, currentTime) - info.getClaimed();
    }

    public void claimTokens(String beneficiary, long amount) {
        VestingInfo info = vestingMap.get(beneficiary);
        if (info != null) info.setClaimed(info.getClaimed() + amount);
    }

    static class VestingInfo {
        private long totalAmount;
        private long startTime;
        private long duration;
        private long claimed;

        public VestingInfo(long totalAmount, long startTime, long duration) {
            this.totalAmount = totalAmount;
            this.startTime = startTime;
            this.duration = duration;
            this.claimed = 0;
        }

        public long getTotalAmount() { return totalAmount; }
        public long getStartTime() { return startTime; }
        public long getDuration() { return duration; }
        public long getClaimed() { return claimed; }
        public void setClaimed(long claimed) { this.claimed = claimed; }
    }
}
