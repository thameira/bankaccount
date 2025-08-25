package ada.tech.lms.domain;

import java.util.Objects;

public abstract class BankAccount {
    protected String accountNumber;
    protected User owner;
    protected double balance;

    public BankAccount(){
    }

    public BankAccount(String accountNumber, User owner, double balance) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = balance;
    }

    public  void withdraw(double amount){
        if (amount <= 0) {
            throw new IllegalArgumentException("Saque deve ser maior que zero.");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }
        this.balance -= amount;
    }

    public void deposit(double amount) {
        if (amount <=0){
            throw  new IllegalArgumentException("Depósito deve ser maior que zero. ");
        }
        this.balance += amount;
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

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", owner=" + owner +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankAccount)) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(accountNumber, that.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}
