package projetoBanco;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Banco banco = new Banco();
        int opcao;

        do {
            System.out.println("\033[H\033[2J"); // Limpa o console (funciona em alguns terminais)
            System.out.flush();

            System.out.println("🏦 == BEM-VINDO AO SUPER BANCO! ==");
            System.out.println("\n======= 📋 MENU =======");
            System.out.println("1️⃣  Cadastrar Cliente");
            System.out.println("2️⃣  Listar Clientes");
            System.out.println("3️⃣  Buscar Cliente por CPF");
            System.out.println("0️⃣  Sair");
            System.out.print("\n👉 Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    System.out.print("Nome completo: ");
                    String nome = scanner.nextLine().trim();

                    // CPF (validação simples: 11 dígitos numéricos)
                    String cpf;
                    do {
                        System.out.print("CPF (somente números, 11 dígitos): ");
                        cpf = scanner.nextLine().trim();
                        if (cpf.length() != 11 || !cpf.matches("\\d+")) {
                            System.out.println("❌ CPF inválido! Digite exatamente 11 dígitos.");
                        }
                    } while (cpf.length() != 11 || !cpf.matches("\\d+"));

                    System.out.print("Telefone (ex: (11) 99999-9999): ");
                    String telefone = scanner.nextLine().trim();

                    // Data de nascimento
                    LocalDate dataNasc = null;
                    while (dataNasc == null) {
                        System.out.print("Data de nascimento (dd/MM/yyyy): ");
                        String dataStr = scanner.nextLine().trim();
                        try {
                            dataNasc = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        } catch (Exception e) {
                            System.out.println("❌ Formato inválido. Tente novamente.");
                        }
                    }

                    System.out.print("Endereço: ");
                    String endereco = scanner.nextLine().trim();

                    System.out.print("Saldo inicial (R$): ");
                    double saldo = scanner.nextDouble();
                    scanner.nextLine(); // Limpar buffer

                    Cliente cliente = new Cliente(nome, cpf, saldo, telefone, dataNasc, endereco);
                    banco.adicionarCliente(cliente);
                    break;

                case 2:
                    banco.listarClientes();
                    System.out.println("pressione ENTER para continuar...");
                    scanner.nextLine();
                    break;

                case 3:
                    System.out.print("🔍 Digite o CPF do cliente: ");
                    String buscaCpf = scanner.nextLine().trim();
                    Cliente encontrado = banco.buscarPorCpf(buscaCpf);
                    if (encontrado != null) {
                        System.out.println("\n✅ Cliente encontrado:\n");
                        System.out.println(encontrado);
                    } else {
                        System.out.println("\n❌ Cliente não encontrado.");
                    }
                    System.out.println("pressione ENTER para continuar...");
                    scanner.nextLine();
                    break;

                case 0:
                    System.out.println("\n👋 Obrigado por usar o Super Banco! Até logo!");
                    break;

                default:
                    System.out.println("\n⚠️ Opção inválida! Tente novamente.");
                    try { Thread.sleep(1500); } catch (Exception ignored) {}
            }

        } while (opcao != 0);

        scanner.close();
    }
}