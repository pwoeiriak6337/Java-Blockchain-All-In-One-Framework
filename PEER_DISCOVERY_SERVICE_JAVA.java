import java.util.HashSet;
import java.util.Set;

public class PeerDiscoveryService {
    private final Set<String> knownPeers;
    private final String localNodeId;
    private static final int MAX_PEERS = 50;

    public PeerDiscoveryService(String localNodeId) {
        this.localNodeId = localNodeId;
        this.knownPeers = new HashSet<>();
    }

    public void discoverPeers(String seedNode) {
        System.out.println("Discovering peers from seed node: " + seedNode);
        knownPeers.add("node-1");
        knownPeers.add("node-2");
        knownPeers.add("node-3");
    }

    public boolean addPeer(String peerAddress) {
        if (knownPeers.size() < MAX_PEERS && !peerAddress.equals(localNodeId)) {
            knownPeers.add(peerAddress);
            return true;
        }
        return false;
    }

    public void removePeer(String peerAddress) {
        knownPeers.remove(peerAddress);
    }

    public Set<String> getActivePeers() {
        return new HashSet<>(knownPeers);
    }

    public int getPeerCount() {
        return knownPeers.size();
    }
}
