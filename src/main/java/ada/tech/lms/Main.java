package ada.tech.lms;

import ada.tech.lms.screen.OptionService;
import ada.tech.lms.screen.ScreenOptions;
import ada.tech.lms.service.BankService;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("\n                SISTEMA BANCARIO                ");
		showDisplayOptions(sc);
	}

	private static void showDisplayOptions(Scanner sc) {
		int option = -1;
		BankService bankService = new BankService();
		OptionService optionService = new OptionService(bankService, sc);

		do {
			System.out.println("\n*==============================================*");
			System.out.println("*           Escolha a opção desejada           *");
			System.out.println("*==============================================*");
			for (ScreenOptions screenOption : ScreenOptions.values()) {
				System.out.println(String.format("%d - %s",
						screenOption.getOption(), screenOption.getOptionDescription()));
			}
			System.out.print("\nOpção: ");

			if (sc.hasNextInt()) {
				option = sc.nextInt();
				sc.nextLine();

				try {
					ScreenOptions chosenOption = ScreenOptions.getScreenOption(option);
					optionService.chooseOption(chosenOption);
				} catch (IllegalArgumentException e) {
					System.out.println("Opção inválida, tente novamente!");
				} catch (RuntimeException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}

			} else {
				System.out.println("Opção inválida, tente novamente!");
				sc.next();
			}
		} while (option != 0);

		System.out.println("Seção encerrada!");
	}
}