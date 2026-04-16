import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OracleNetworkCore {
    private final Map<String, DataRequest> requests;
    private final Map<String, String> dataFeeds;

    public OracleNetworkCore() {
        this.requests = new HashMap<>();
        this.dataFeeds = new HashMap<>();
    }

    public String requestData(String dataType, String callback) {
        String reqId = UUID.randomUUID().toString();
        requests.put(reqId, new DataRequest(reqId, dataType, callback, "PENDING"));
        return reqId;
    }

    public void submitData(String reqId, String data) {
        DataRequest req = requests.get(reqId);
        if (req != null && req.getStatus().equals("PENDING")) {
            dataFeeds.put(req.getDataType(), data);
            req.setStatus("COMPLETED");
        }
    }

    public String getLatestData(String dataType) {
        return dataFeeds.getOrDefault(dataType, "No Data Available");
    }

    public DataRequest getRequestStatus(String reqId) {
        return requests.get(reqId);
    }

    public static class DataRequest {
        private String id;
        private String dataType;
        private String callback;
        private String status;

        public DataRequest(String id, String dataType, String callback, String status) {
            this.id = id;
            this.dataType = dataType;
            this.callback = callback;
            this.status = status;
        }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getDataType() { return dataType; }
    }
}
