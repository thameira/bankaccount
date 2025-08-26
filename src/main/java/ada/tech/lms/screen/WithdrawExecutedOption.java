package ada.tech.lms.screen.executed;

import ada.tech.lms.domain.BankAccount;
import ada.tech.lms.domain.User;
import ada.tech.lms.screen.ExecutedOption;
import ada.tech.lms.service.BankService;
import ada.tech.lms.util.InputUtils;

import java.util.Scanner;

public class WithdrawExecutedOption implements ExecutedOption {

	private final BankService bankService;
	private final Scanner scanner;
	private final User user;

	public WithdrawExecutedOption(BankService bankService, Scanner scanner, User user) {
		this.bankService = bankService;
		this.scanner = scanner;
		this.user = user;
	}

	@Override
	public void execute() {
		System.out.println("\n*----------------------------------------------*");
		System.out.println("*                     SAQUE                    *");
		System.out.println("*----------------------------------------------*");

		double value = InputUtils.readDouble(scanner, "Valor do saque: ");

		BankAccount account = bankService.findAccountByUser(user);
		account.withdraw(value);

		bankService.saveTransaction(user.getCpf(), "Saque   ", -value);
		bankService.saveAccount(account);

		System.out.println("Saque realizado com sucesso.");
	}
}
