package ada.tech.lms.domain;

public abstract class BankAccount {
    protected String accountNumber;
    protected User owner;
    protected double balance;

    public BankAccount() {}

    public BankAccount(String accountNumber, User owner, double balance) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = balance;
    }

    // Saque básico (para contas simples)
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Saque deve ser maior que zero.");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }
        balance -= amount;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Depósito deve ser maior que zero.");
        }
        balance += amount;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public User getOwner() {
        return owner;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
