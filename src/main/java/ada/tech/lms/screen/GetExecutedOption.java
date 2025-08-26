package ada.tech.lms.screen;

import ada.tech.lms.domain.BankAccount;
import ada.tech.lms.domain.SpecialAccount;
import ada.tech.lms.domain.User;
import ada.tech.lms.service.BankService;

public class GetExecutedOption implements ExecutedOption {
	private final BankService bankService;
	private final User user;

	public GetExecutedOption(BankService bankService, User user) {
		this.bankService = bankService;
		this.user = user;
	}

	@Override
	public void execute() {
		BankAccount account = bankService.findAccountByUser(user);

		System.out.println("\n*----------------------------------------------*");
		System.out.println("*                     SALDO                    *");
		System.out.println("*----------------------------------------------*");
		System.out.printf("Titular.........: %s%n", user.getName());
		System.out.printf("Conta...........: %s%n", account.getAccountNumber());
		System.out.printf("Saldo...........: R$ %.2f%n", account.getBalance());

		if (account instanceof SpecialAccount sa) {
			System.out.printf("Limite..........: R$ %.2f%n", sa.getLimit());
			System.out.printf("Saldo disponível: R$ %.2f%n", account.getBalance() + sa.getLimit());
		}
	}
}
