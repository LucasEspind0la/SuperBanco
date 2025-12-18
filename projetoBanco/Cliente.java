package projetoBanco;

import java.time.LocalDate;

public class Cliente {

    private String nome;
    private String cpf;
    private double saldo;
    private String telefone;
    private LocalDate dataNascimento;
    private String endereco;

    // Construtor completo
    public Cliente(String nome, String cpf, double saldo, String telefone,
                   LocalDate dataNascimento, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.saldo = saldo;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
    }

    // Getters
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public double getSaldo() { return saldo; }
    public String getTelefone() { return telefone; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public String getEndereco() { return endereco; }

    // Setters
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    @Override
    public String toString() {
        return "📇 Nome: " + nome + "\n" +
               "🪪 CPF: " + cpf + "\n" +
               "📞 Telefone: " + telefone + "\n" +
               "🎂 Data de Nascimento: " +
               (dataNascimento != null ? dataNascimento.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "não informada") + "\n" +
               "🏠 Endereço: " + endereco + "\n" +
               "💰 Saldo Atual: R$ " + String.format("%.2f", saldo) + "\n";
    }
}