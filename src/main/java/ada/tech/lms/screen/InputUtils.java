package ada.tech.lms.util;

import java.util.Scanner;

public class InputUtils {

    // Lê número double validado
    public static double readDouble(Scanner sc, String message) {
        double value;
        while (true) {
            System.out.print(message);
            if (sc.hasNextDouble()) {
                value = sc.nextDouble();
                sc.nextLine(); // consome o Enter
                break;
            } else {
                System.out.println("Valor inválido! Digite apenas números.");
                sc.next(); // descarta entrada inválida
            }
        }
        return value;
    }

    // Lê CPF apenas numérico (qualquer quantidade de dígitos)
    public static String readCpf(Scanner sc) {
        String cpf;
        while (true) {
            System.out.print("Informe o CPF: ");
            cpf = sc.next();
            if (!cpf.matches("\\d+")) {
                System.out.println("CPF inválido! Digite apenas números.");
                continue;
            }
            break;
        }
        return cpf;
    }

    // Lê opção de Sim/Não validada
    public static boolean readYesNo(Scanner sc, String message) {
        String opcao;
        while (true) {
            System.out.print(message);
            opcao = sc.next().trim().toLowerCase();
            if (opcao.equals("s")) {
                return true;  // sim
            } else if (opcao.equals("n")) {
                return false; // não
            } else {
                System.out.println("Opção inválida! Digite apenas 's' para Sim ou 'n' para Não.");
            }
        }
    }
}
