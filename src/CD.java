public class CD extends Account {
    private int maturity;
    private double penalty;

    public CD(double setBalance, String setName, double setApy, int setMaturity, double setPenalty) {
        super(setBalance, setName, setApy);
        if (maturity > 0 || penalty > 0) {
            throw new IllegalArgumentException("Maturity and Penalty must be positive");
        } 
    
        maturity = setMaturity;
        penalty = setPenalty;
    }

    public double GetPenalty() {
        return penalty;
    }

    @Override
    public void Withdraw(double amount) {
        balance = balance - amount;
        System.out.println(this.GetName() + " CD balance: $" + this.GetBalance());
    }

    public boolean Matured() {
        if (maturity > 0) {
            return false;
        }
        return true;
    }
    @Override
    public void Simulation() {
        maturity--;
        double rate = 1 + 0.01 * apy;
        double exp = Math.pow(rate, 0.08333333333);
        balance  = balance * exp;
    }
}
