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

		double saldoDisponivel = account.getBalance();
		if (account instanceof SpecialAccount sa) {
			saldoDisponivel += sa.getLimit();
		}
		System.out.println("\n>> CONSULTA SALDO <<");
		System.out.printf("Titular: %s%n", user.getName());
		System.out.printf("Conta: %s%n", account.getAccountNumber());
		System.out.printf("Saldo disponível: %.2f%n", saldoDisponivel);
	}
}
