package projetoBanco;

import java.util.ArrayList;

public class Banco {

    private ArrayList<Cliente> clientes = new ArrayList<>();

    public void adicionarCliente(Cliente cliente) {
        if (buscarPorCpf(cliente.getCpf()) != null) {
            System.out.println("⚠️ ERRO! CPF já cadastrado!");
            System.out.println("⚠️ Verifique se digitou corretamente.");
            
            return;
        }
        clientes.add(cliente);
        System.out.println("\n✅ Cliente cadastrado com sucesso!\n");
    }
    public boolean removerClientePorCpf(String cpf) {
        return clientes.removeIf(c -> c.getCpf().equals(cpf));
    }

    public java.util.List<Cliente> getClientes() {
        return new java.util.ArrayList<>(clientes); // cópia segura
    }

    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("📭 Nenhum cliente cadastrado.");
            return;
        }
        System.out.println("\n====== LISTA DE CLIENTES ======\n");
        for (Cliente c : clientes) {
            System.out.println(c);
            System.out.println("------------------------------\n");
        }
        
    }

    public Cliente buscarPorCpf(String cpf) {
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                return c;
            }
        }
        return null;
    }
}