package ada.tech.lms.domain;

public class SpecialAccount extends BankAccount {
    private double limit;

    public SpecialAccount(String accountNumber, User owner, double balance, double limit) {
        super(accountNumber, owner, balance);
        this.limit = limit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Saque deve ser maior que zero.");
        }
        if (amount > balance + limit) {
            throw new IllegalArgumentException("O valor excede o saldo e o limite.");
        }
        balance -= amount;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }
}
