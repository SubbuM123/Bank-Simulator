import java.util.HashMap;

public class Bank {
    private HashMap<String, Account> accounts = new HashMap<String, Account>();
    private HashMap<String, CD> cds = new HashMap<String, CD>();
    private double savings_apy;
    private int cd_counter = 0;


    public Bank(double setAPY) {
        savings_apy = setAPY;

        accounts.put("Savings", new Account(0, "Savings", savings_apy));
        accounts.put("Checking", new Account(0, "Checking", 0.25));
    }
    public void Deposit(double amount, String acc) {
        Account s = accounts.get(acc);
        s.Deposit(amount);
    }
    public void Withdraw(double amount, String acc) {
        Account c = accounts.get(acc);
        c.Withdraw(amount);
    }
    public void NewCD(double setBalance, String setName, double setApy, int setMaturity, double setPenalty) {
        if (cd_counter >= 5) {
            throw new IllegalArgumentException("Only a maximum of 5 CDs can be made");
        }
        CD cd = new CD(setBalance, setName, setApy, setMaturity, setPenalty);
        cds.put(setName, cd);
        cd_counter++;
    }
    public void TransferFundsAcc(double amount, String from, String to) {
        if (amount > accounts.get(from).GetBalance()) {
            throw new IllegalArgumentException("Balance in " + from + " is not sufficient");
        }
        accounts.get(from).Withdraw(amount);
        accounts.get(to).Deposit(amount);
    }
    public void TransferFundsCD(double amount, String from, String to) {
        if (amount > cds.get(from).GetBalance()) {
            throw new IllegalArgumentException("Balance in " + from + " is not sufficient");
        }
        CD cd = cds.get(from);
        cd.Withdraw(amount);
        accounts.get(to).Deposit(amount * (1 - (0.01 * cd.GetPenalty())));
    }
    public void EmptyCD(CD cd) {
        accounts.get("Savings").Deposit(cd.GetBalance());
        cds.remove(cd.GetName());
    }
    public double Total() {
        double t = 0;
        for (CD c : cds.values()) {
            t += c.GetBalance();
        }
        t += accounts.get("Savings").GetBalance() + accounts.get("Checking").GetBalance();
        return t;
    }
    public void Simulate(int months) {
       for (int i = 0; i < months; ++i) {
            for (CD c : cds.values()) {
                if (!c.Matured()) {
                    c.Simulation();
                } else {
                    EmptyCD(c);
                }
            }
        accounts.get("Savings").Simulation();
       }
       System.out.println(Total());
    }
    // public void SimulateSalary(int months, double salary) {
    //    iterate through each month and check and empty as needed
    // }

}
