package ada.tech.lms.screen;

import ada.tech.lms.service.BankService;

import java.util.Scanner;

public class OptionService {

	private BankService bankService;
	private Scanner scanner;

	public OptionService (BankService bankService, Scanner scanner){
		this.bankService = bankService;
		this.scanner = scanner;
	}

	public void chooseOption(ScreenOptions screenOptions) {
		ExecutedOption executedOption = null;
		var identifyAccountScreen = new IdentifyAccountScreen(bankService);

		try {
			switch (screenOptions) {
				case WITHDRAW -> executedOption = new ada.tech.lms.screen.executed.WithdrawExecutedOption(bankService, scanner, identifyAccountScreen.init(scanner));
				case CREATE_ACCOUNT -> executedOption = new ada.tech.lms.screen.executed.CreateAccountExecutedOption(scanner, bankService);
				case DEPOSIT -> executedOption = new ada.tech.lms.screen.executed.DepositExecutedOption(bankService, scanner, identifyAccountScreen.init(scanner));
				case GET_BALANCE -> executedOption = new GetExecutedOption(bankService, identifyAccountScreen.init(scanner));
				case GET_STATEMENT -> executedOption = new GetStatementExecutedOption(bankService, scanner);
				default -> System.exit(0);
			}

			if (executedOption != null) {
				executedOption.execute();
			}

		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}


}
