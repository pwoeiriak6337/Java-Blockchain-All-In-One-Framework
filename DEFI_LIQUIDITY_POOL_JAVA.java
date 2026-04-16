import java.math.BigDecimal;
import java.math.RoundingMode;

public class DeFiLiquidityPool {
    private BigDecimal tokenXReserve;
    private BigDecimal tokenYReserve;
    private BigDecimal totalLP;
    private static final BigDecimal FEE_RATE = new BigDecimal("0.003");

    public DeFiLiquidityPool(BigDecimal initX, BigDecimal initY) {
        this.tokenXReserve = initX;
        this.tokenYReserve = initY;
        this.totalLP = initX.multiply(initY).sqrt(new java.math.MathContext(10));
    }

    public BigDecimal addLiquidity(BigDecimal xAmount, BigDecimal yAmount) {
        BigDecimal lpMinted = xAmount.multiply(totalLP).divide(tokenXReserve, 10, RoundingMode.HALF_UP);
        tokenXReserve = tokenXReserve.add(xAmount);
        tokenYReserve = tokenYReserve.add(yAmount);
        totalLP = totalLP.add(lpMinted);
        return lpMinted;
    }

    public BigDecimal removeLiquidity(BigDecimal lpAmount) {
        BigDecimal xReturn = lpAmount.multiply(tokenXReserve).divide(totalLP, 10, RoundingMode.HALF_UP);
        BigDecimal yReturn = lpAmount.multiply(tokenYReserve).divide(totalLP, 10, RoundingMode.HALF_UP);
        tokenXReserve = tokenXReserve.subtract(xReturn);
        tokenYReserve = tokenYReserve.subtract(yReturn);
        totalLP = totalLP.subtract(lpAmount);
        return xReturn;
    }

    public BigDecimal swapXForY(BigDecimal xIn) {
        BigDecimal xInWithFee = xIn.multiply(BigDecimal.ONE.subtract(FEE_RATE));
        BigDecimal yOut = tokenYReserve.multiply(xInWithFee).divide(tokenXReserve.add(xInWithFee), 10, RoundingMode.HALF_UP);
        tokenXReserve = tokenXReserve.add(xIn);
        tokenYReserve = tokenYReserve.subtract(yOut);
        return yOut;
    }

    public BigDecimal getPriceRatio() {
        return tokenXReserve.divide(tokenYReserve, 10, RoundingMode.HALF_UP);
    }
}
