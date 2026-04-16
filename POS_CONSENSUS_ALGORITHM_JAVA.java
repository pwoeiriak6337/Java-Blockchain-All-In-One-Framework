import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PoSConsensusAlgorithm {
    private final Map<String, Integer> validatorStakes;
    private String currentValidator;

    public PoSConsensusAlgorithm() {
        this.validatorStakes = new HashMap<>();
    }

    public void registerValidator(String address, int stake) {
        if (stake > 0) {
            validatorStakes.put(address, stake);
        }
    }

    public String selectValidator() {
        int totalStake = validatorStakes.values().stream().mapToInt(Integer::intValue).sum();
        if (totalStake == 0) return null;

        Random random = new Random();
        int randomPoint = random.nextInt(totalStake) + 1;
        int currentSum = 0;

        for (Map.Entry<String, Integer> entry : validatorStakes.entrySet()) {
            currentSum += entry.getValue();
            if (currentSum >= randomPoint) {
                this.currentValidator = entry.getKey();
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean validateBlock(String validatorAddress) {
        return validatorAddress.equals(currentValidator) && validatorStakes.containsKey(validatorAddress);
    }

    public void removeValidator(String address) {
        validatorStakes.remove(address);
    }

    public Map<String, Integer> getValidatorStakes() {
        return new HashMap<>(validatorStakes);
    }
}
