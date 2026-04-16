import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StateChannelManager {
    private final Map<String, StateChannel> channels;

    public StateChannelManager() {
        this.channels = new HashMap<>();
    }

    public String openChannel(String partyA, String partyB, long deposit) {
        String channelId = UUID.randomUUID().toString();
        channels.put(channelId, new StateChannel(partyA, partyB, deposit));
        return channelId;
    }

    public boolean updateChannelState(String channelId, long newBalanceA, long newBalanceB, String signature) {
        StateChannel channel = channels.get(channelId);
        if (channel != null && channel.isOpen()) {
            channel.setBalances(newBalanceA, newBalanceB);
            return true;
        }
        return false;
    }

    public boolean closeChannel(String channelId) {
        StateChannel channel = channels.get(channelId);
        if (channel != null) {
            channel.setOpen(false);
            return true;
        }
        return false;
    }

    public StateChannel getChannel(String channelId) {
        return channels.get(channelId);
    }

    static class StateChannel {
        private String partyA;
        private String partyB;
        private long balanceA;
        private long balanceB;
        private boolean isOpen;

        public StateChannel(String partyA, String partyB, long deposit) {
            this.partyA = partyA;
            this.partyB = partyB;
            this.balanceA = deposit;
            this.balanceB = 0;
            this.isOpen = true;
        }

        public void setBalances(long a, long b) {
            this.balanceA = a;
            this.balanceB = b;
        }

        public boolean isOpen() { return isOpen; }
        public void setOpen(boolean open) { isOpen = open; }
    }
}
