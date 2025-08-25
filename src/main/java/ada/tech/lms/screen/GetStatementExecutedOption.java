package ada.tech.lms.screen;

import ada.tech.lms.domain.BankAccount;
import ada.tech.lms.domain.SpecialAccount;
import ada.tech.lms.domain.User;
import ada.tech.lms.service.BankService;
import ada.tech.lms.util.InputUtils;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GetStatementExecutedOption implements ExecutedOption {
    private final BankService bankService;
    private final Scanner scanner;

    public GetStatementExecutedOption(BankService bankService, Scanner scanner) {
        this.bankService = bankService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        // validação consistente de CPF
        String cpf = InputUtils.readCpf(scanner);

        User user;
        BankAccount account;
        try {
            user = bankService.findUser(cpf);
            account = bankService.findAccountByUser(user);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return;
        }

        // Cálculo do saldo disponível
        double saldoDisponivel = account.getBalance();
        if (account instanceof SpecialAccount sa) {
            saldoDisponivel += sa.getLimit();
        }

        // Cabeçalho
        System.out.println("\n>> EXTRATO <<");
        System.out.printf("Titular: %s%n", user.getName());
        System.out.printf("Conta: %s%n", account.getAccountNumber());
        System.out.printf("Saldo disponível: %.2f%n", saldoDisponivel);
        System.out.println("-------------------------------------");

        // Leitura do arquivo de transações
        File file = new File("data/transacoes_" + cpf + ".txt");
        if (!file.exists()) {
            System.out.println("Nenhuma transação encontrada para este CPF.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            // Ordenar por data/hora decrescente
            lines.sort(Comparator.comparing((String l) -> {
                try {
                    String timestamp = l.split("\\|")[0].trim();
                    return LocalDateTime.parse(timestamp, formatter);
                } catch (Exception e) {
                    return LocalDateTime.MIN;
                }
            }).reversed());

            // Exibir transações
            lines.forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Erro ao ler o extrato: " + e.getMessage());
        }
    }
}
