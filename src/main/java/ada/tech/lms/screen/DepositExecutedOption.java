package ada.tech.lms.screen.executed;

import ada.tech.lms.domain.BankAccount;
import ada.tech.lms.domain.User;
import ada.tech.lms.screen.ExecutedOption;
import ada.tech.lms.service.BankService;
import ada.tech.lms.util.InputUtils;

import java.util.Scanner;

public class DepositExecutedOption implements ExecutedOption {

	private final BankService bankService;
	private final Scanner scanner;
	private final User user;

	public DepositExecutedOption(BankService bankService, Scanner scanner, User user) {
		this.bankService = bankService;
		this.scanner = scanner;
		this.user = user;
	}

	@Override
	public void execute() {
		System.out.println("\n*----------------------------------------------*");
		System.out.println("*                   DEPOSITO                   *");
		System.out.println("*----------------------------------------------*");
		// valor validado com InputUtils
		double value = InputUtils.readDouble(scanner, "Valor do depósito: ");

		BankAccount account = bankService.findAccountByUser(user);
		account.deposit(value);

		bankService.saveTransaction(user.getCpf(), "Depósito", value);
		bankService.saveAccount(account);

		System.out.println("Depósito realizado com sucesso.");
	}
}
