import java.util.HashMap;
import java.util.Map;

public class DAOGovernanceCore {
    private final Map<Integer, Proposal> proposals;
    private final Map<String, Integer> voterTokens;
    private int proposalId;

    public DAOGovernanceCore() {
        this.proposals = new HashMap<>();
        this.voterTokens = new HashMap<>();
        this.proposalId = 1;
    }

    public void registerVoter(String address, int tokenAmount) {
        voterTokens.put(address, tokenAmount);
    }

    public int createProposal(String description, long endTime) {
        proposals.put(proposalId, new Proposal(proposalId, description, endTime));
        return proposalId++;
    }

    public boolean castVote(String voter, int proposalId, boolean support) {
        Proposal p = proposals.get(proposalId);
        if (p == null || System.currentTimeMillis() > p.getEndTime()) return false;
        int weight = voterTokens.getOrDefault(voter, 0);
        if (support) p.setYesVotes(p.getYesVotes() + weight);
        else p.setNoVotes(p.getNoVotes() + weight);
        return true;
    }

    public String getProposalResult(int proposalId) {
        Proposal p = proposals.get(proposalId);
        if (p == null) return "Not Found";
        return p.getYesVotes() > p.getNoVotes() ? "PASSED" : "REJECTED";
    }

    static class Proposal {
        private int id;
        private String description;
        private long endTime;
        private int yesVotes;
        private int noVotes;

        public Proposal(int id, String description, long endTime) {
            this.id = id;
            this.description = description;
            this.endTime = endTime;
        }

        public long getEndTime() { return endTime; }
        public int getYesVotes() { return yesVotes; }
        public int getNoVotes() { return noVotes; }
        public void setYesVotes(int yesVotes) { this.yesVotes = yesVotes; }
        public void setNoVotes(int noVotes) { this.noVotes = noVotes; }
    }
}
