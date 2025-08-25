package ada.tech.lms.screen;

public enum ScreenOptions {

	CREATE_ACCOUNT(1, "ABERTURA DE CONTA"),
	WITHDRAW(2, "SAQUE"),
	DEPOSIT(3, "DEPOSITO"),
	GET_BALANCE(4,"CONSULTA SALDO"),
	GET_STATEMENT(5,"EXTRATO"),
	NO_OPTION(0,"Sair");

	private int option;
	private String optionDescription;

	private ScreenOptions(int option, String optionDescription){
		this.option = option;
		this.optionDescription = optionDescription;
	}

	public int getOption() {
		return option;
	}

	public String getOptionDescription() {
		return optionDescription;
	}

	public static ScreenOptions getScreenOption(int option) {
		for (ScreenOptions screenOption : ScreenOptions.values()) {
			if (screenOption.getOption() == option) {
				return screenOption;
			}
		}
		throw new IllegalArgumentException("Opção inválida, digite novamente.");
	}
}
