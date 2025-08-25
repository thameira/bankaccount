package ada.tech.lms.service;

import ada.tech.lms.domain.BankAccount;
import ada.tech.lms.domain.User;
import ada.tech.lms.persistence.FilePersistenceService;

import java.util.ArrayList;
import java.util.List;

public class BankService {
    private final List<BankAccount> accounts = new ArrayList<>();
    private final FilePersistenceService persistence = new FilePersistenceService();

    public void addAccount(BankAccount account) {
        persistence.saveAccount(account);
        persistence.saveUser(account.getOwner());
        accounts.add(account);
    }

    public void deposit(String accountNumber, double amount) {
        BankAccount account = findAccount(accountNumber);
        account.deposit(amount);
        persistence.saveAccount(account);
    }

    public void withdraw(String accountNumber, double amount) {
        BankAccount account = findAccount(accountNumber);
        account.withdraw(amount);
        persistence.saveAccount(account);
    }

    public double checkBalance(String accountNumber) {
        return findAccount(accountNumber).getBalance();
    }

    public BankAccount findAccount(String accountNumber) {
        return accounts.stream()
                .filter(a -> a.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
    }

    public User findUser(String documentNumber){
        // Primeiro tenta buscar na memória
        for (BankAccount account : accounts){
            if(account.getOwner().getCpf().equals(documentNumber)){
                return account.getOwner();
            }
        }
        // Se não encontrar, tenta carregar do arquivo
        return persistence.loadUser(documentNumber);
    }

    public BankAccount findAccountByUser(User user) {
        // Primeiro procura na memória
        for (BankAccount account : accounts) {
            if (account.getOwner().getCpf().equals(user.getCpf())) {
                return account;
            }
        }

        // Tenta carregar do arquivo
        try {
            BankAccount loadedAccount = persistence.loadAccount(user); // <-- Agora passa o user já carregado
            accounts.add(loadedAccount);
            return loadedAccount;
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Conta não encontrada para o usuário informado.");
        }
    }

    public void saveTransaction(String cpf, String tipo, double valor) {
        persistence.saveTransaction(cpf, tipo, valor);
    }

    public void saveAccount(BankAccount account) {
        persistence.saveAccount(account);
    }
}