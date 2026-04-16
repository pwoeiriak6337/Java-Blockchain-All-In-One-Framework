public class TransactionFeeEngine {
    private static final long BASE_FEE = 100;
    private static final long PER_BYTE_FEE = 1;
    private static final long PRIORITY_FEE_MULTIPLIER = 2;

    public long calculateStandardFee(int dataSize) {
        return BASE_FEE + (long) dataSize * PER_BYTE_FEE;
    }

    public long calculatePriorityFee(int dataSize) {
        return (BASE_FEE + (long) dataSize * PER_BYTE_FEE) * PRIORITY_FEE_MULTIPLIER;
    }

    public boolean isFeeSufficient(long paidFee, int dataSize, boolean isPriority) {
        long required = isPriority ? calculatePriorityFee(dataSize) : calculateStandardFee(dataSize);
        return paidFee >= required;
    }

    public long getMinimumFee() {
        return BASE_FEE;
    }
}
