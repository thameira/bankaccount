package ada.tech.lms.screen.executed;

import ada.tech.lms.screen.ExecutedOption;
import ada.tech.lms.domain.SimpleAccount;
import ada.tech.lms.domain.SpecialAccount;
import ada.tech.lms.domain.User;
import ada.tech.lms.service.BankService;
import ada.tech.lms.util.InputUtils;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class CreateAccountExecutedOption implements ExecutedOption {

	private final Scanner scanner;
	private final BankService bankService;

	public CreateAccountExecutedOption(Scanner scanner, BankService bankService) {
		this.scanner = scanner;
		this.bankService = bankService;
	}

	@Override
	public void execute() {
		System.out.println("\n>> ABERTURA DE CONTA <<");

		String cpf = InputUtils.readCpf(scanner); // aceita só números
		scanner.nextLine(); // consome Enter do next()

		// Verifica se já existe conta para esse CPF
		try {
			bankService.findUser(cpf);
			System.out.println("Já existe uma conta cadastrada para este CPF!");
			return; // encerra execução sem criar conta
		} catch (RuntimeException e) {
			// não existe, pode continuar
		}

		System.out.print("Nome: ");
		String name = scanner.nextLine();

		double saldoInicial = InputUtils.readDouble(scanner, "Saldo inicial: ");

		String generatedAccountNumber = generateAccountNumber();

		boolean contaEspecial = InputUtils.readYesNo(scanner, "Conta especial? (s/n): ");

		if (contaEspecial) {
			double limite = InputUtils.readDouble(scanner, "Limite: ");
			bankService.addAccount(new SpecialAccount(generatedAccountNumber, new User(cpf, name), saldoInicial, limite));
		} else {
			bankService.addAccount(new SimpleAccount(generatedAccountNumber, new User(cpf, name), saldoInicial));
		}

		System.out.println("Conta criada com sucesso.\n");
	}

	// Gera um número de conta com 6 dígitos (mantém zeros à esquerda)
	private String generateAccountNumber() {
		StringBuilder sb = new StringBuilder(6);
		for (int i = 0; i < 6; i++) {
			sb.append(ThreadLocalRandom.current().nextInt(10)); // 0..9
		}
		return sb.toString();
	}
}
