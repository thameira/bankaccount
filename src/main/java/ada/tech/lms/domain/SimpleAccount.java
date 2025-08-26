package ada.tech.lms.domain;

public class SimpleAccount extends BankAccount {

    public SimpleAccount(String accountNumber, User owner, double balance) {
        super(accountNumber, owner, balance);
    }
}
