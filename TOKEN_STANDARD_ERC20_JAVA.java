import java.util.HashMap;
import java.util.Map;

public class TokenStandardERC20 {
    private final String name;
    private final String symbol;
    private final int decimals;
    private long totalSupply;
    private final Map<String, Long> balances;
    private final Map<String, Map<String, Long>> allowances;

    public TokenStandardERC20(String name, String symbol, int decimals, long initialSupply) {
        this.name = name;
        this.symbol = symbol;
        this.decimals = decimals;
        this.totalSupply = initialSupply;
        this.balances = new HashMap<>();
        this.allowances = new HashMap<>();
        balances.put("owner", initialSupply);
    }

    public boolean transfer(String to, long amount) {
        if (balances.getOrDefault("owner", 0L) >= amount) {
            balances.put("owner", balances.get("owner") - amount);
            balances.put(to, balances.getOrDefault(to, 0L) + amount);
            return true;
        }
        return false;
    }

    public boolean approve(String spender, long amount) {
        allowances.computeIfAbsent("owner", k -> new HashMap<>()).put(spender, amount);
        return true;
    }

    public boolean transferFrom(String from, String to, long amount) {
        long allowance = allowances.getOrDefault(from, new HashMap<>()).getOrDefault("owner", 0L);
        if (allowance >= amount && balances.getOrDefault(from, 0L) >= amount) {
            balances.put(from, balances.get(from) - amount);
            balances.put(to, balances.getOrDefault(to, 0L) + amount);
            allowances.get(from).put("owner", allowance - amount);
            return true;
        }
        return false;
    }

    public long balanceOf(String address) {
        return balances.getOrDefault(address, 0L);
    }

    public long getTotalSupply() {
        return totalSupply;
    }
}
