package ada.tech.lms.persistence;

import ada.tech.lms.domain.BankAccount;
import ada.tech.lms.domain.SimpleAccount;
import ada.tech.lms.domain.SpecialAccount;
import ada.tech.lms.domain.User;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FilePersistenceService {

    private static final String DATA_DIR = "data/";

    // ---------------------- Usuário ----------------------
    public void saveUser(User user) {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) dir.mkdirs();

        String filename = DATA_DIR + "usuario_" + user.getCpf() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("cpf=" + user.getCpf());
            writer.newLine();
            writer.write("nome=" + user.getName());

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar usuário", e);
        }
    }

    public User loadUser(String cpf) {
        String filename = DATA_DIR + "usuario_" + cpf + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            String cpfRead = null;
            String name = null;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length < 2) continue;
                String key = parts[0].trim();
                String value = parts[1].trim();

                if (key.equalsIgnoreCase("cpf")) cpfRead = value;
                if (key.equalsIgnoreCase("nome")) name = value;
            }

            if (cpfRead == null || name == null) {
                throw new RuntimeException("Arquivo de usuário incompleto");
            }

            return new User(cpfRead, name);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler usuário", e);
        }
    }

    public void saveAccount(BankAccount account) {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) dir.mkdirs();

        String filename = DATA_DIR + "conta_" + account.getOwner().getCpf() + ".txt";

        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pt", "BR"));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

            writer.write("tipo=" + (account instanceof SpecialAccount ? "Especial" : "Simples"));
            writer.newLine();
            writer.write("numeroConta=" + account.getAccountNumber());
            writer.newLine();
            writer.write("cpf=" + account.getOwner().getCpf());
            writer.newLine();

            writer.write("saldo=" + nf.format(account.getBalance()));
            writer.newLine();

            if (account instanceof SpecialAccount sa) {
                // grava limite com vírgula
                writer.write("limite=" + nf.format(sa.getLimit()));
                writer.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar conta", e);
        }
    }

    public BankAccount loadAccount(User user) {
        String cpf = user.getCpf();
        String filename = DATA_DIR + "conta_" + cpf + ".txt";
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pt", "BR"));

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            String tipo = null;
            String numeroConta = null;
            double saldo = 0.0;
            double limite = 0.0;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length < 2) continue;
                String key = parts[0].trim();
                String value = parts[1].trim();

                switch (key.toLowerCase()) {
                    case "tipo" -> tipo = value;
                    case "numeroconta" -> numeroConta = value;
                    case "saldo" -> {
                        try {
                            saldo = nf.parse(value).doubleValue();
                        } catch (ParseException e) {
                            throw new RuntimeException("Formato inválido para saldo");
                        }
                    }
                    case "limite" -> {
                        try {
                            limite = nf.parse(value).doubleValue();
                        } catch (ParseException e) {
                            throw new RuntimeException("Formato inválido para limite");
                        }
                    }
                }
            }

            if (tipo == null || numeroConta == null) {
                throw new RuntimeException("Arquivo de conta incompleto");
            }

            BankAccount account;
            if ("Especial".equalsIgnoreCase(tipo)) {
                account = new SpecialAccount(numeroConta, user, saldo, limite);
            } else {
                account = new SimpleAccount(numeroConta, user, saldo);
            }

            return account;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler conta", e);
        }
    }

    public void saveTransaction(String cpf, String tipo, double valor) {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) dir.mkdirs();

        String filename = DATA_DIR + "transacoes_" + cpf + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String linha = String.format("%s | %s | %.2f", LocalDateTime.now().format(formatter), tipo, valor);
            writer.write(linha);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gravar transação", e);
        }
    }
}