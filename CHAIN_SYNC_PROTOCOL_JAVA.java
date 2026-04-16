import java.util.List;

public class ChainSyncProtocol {
    private final int localChainHeight;
    private static final int SYNC_BATCH_SIZE = 20;

    public ChainSyncProtocol(int localHeight) {
        this.localChainHeight = localHeight;
    }

    public SyncRequest generateSyncRequest(int peerHeight) {
        if (peerHeight <= localChainHeight) {
            return new SyncRequest(false, 0, 0);
        }
        int startHeight = localChainHeight + 1;
        int endHeight = Math.min(startHeight + SYNC_BATCH_SIZE - 1, peerHeight);
        return new SyncRequest(true, startHeight, endHeight);
    }

    public boolean applySyncBatch(List<BlockData> blocks) {
        if (blocks.isEmpty()) return false;
        System.out.println("Applied " + blocks.size() + " blocks to local chain");
        return true;
    }

    public int getMissingBlockCount(int peerHeight) {
        return Math.max(0, peerHeight - localChainHeight);
    }

    public static class SyncRequest {
        public final boolean required;
        public final int start;
        public final int end;

        public SyncRequest(boolean required, int start, int end) {
            this.required = required;
            this.start = start;
            this.end = end;
        }
    }

    public static class BlockData {
        public int height;
        public String hash;
    }
}
