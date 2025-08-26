package ada.tech.lms.screen;

import ada.tech.lms.domain.User;
import ada.tech.lms.service.BankService;

import java.util.Scanner;

public class IdentifyAccountScreen {
	private BankService bankService;

	public IdentifyAccountScreen(BankService bankService){
		this.bankService = bankService;
	}

	public User init(Scanner sc){
		String cpf;
		while (true) {
			System.out.print("Informe o cpf da conta: ");
			cpf = sc.next();

			// valida se é numérico
			if (!cpf.matches("\\d+")) {
				System.out.println("CPF inválido! Digite apenas números.");
				continue;
			}
			break;
		}

		return bankService.findUser(cpf);
	}
}

