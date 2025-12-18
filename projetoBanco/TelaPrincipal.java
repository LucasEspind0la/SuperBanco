package projetoBanco;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TelaPrincipal extends JFrame {
    private Banco banco;
    private JTable tabelaClientes;
    private DefaultTableModel modeloTabela;
    private JTextField txtNome, txtCpf, txtTelefone, txtDataNasc, txtEndereco, txtSaldo;
    private JTextField txtBuscaCpf;

    public TelaPrincipal(Banco banco) {
        this.banco = banco;
        setTitle("🏦 Super Banco - Sistema de Gestão");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Ícone (opcional)
        // setIconImage(new ImageIcon("icone.png").getImage());

        // Layout principal
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel superior: título
        JLabel lblTitulo = new JLabel("🏦 SUPER BANCO", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(0, 51, 102));
        add(lblTitulo, BorderLayout.NORTH);

        // Painel central: divisão esquerda/direita
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(450);
        splitPane.setResizeWeight(0.45);

        // ➤ Painel esquerdo: formulário de cadastro e busca
        JPanel painelForm = criarPainelFormulario();
        splitPane.setLeftComponent(new JScrollPane(painelForm));

        // ➤ Painel direito: tabela de clientes
        JPanel painelTabela = criarPainelTabela();
        splitPane.setRightComponent(painelTabela);

        add(splitPane, BorderLayout.CENTER);

        // Carregar clientes iniciais (opcional)
        carregarClientesNaTabela();
    }

    private JPanel criarPainelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("📝 Cadastro de Cliente"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos
        txtNome = criarCampo("Nome completo:", panel, gbc, 0);
        txtCpf = criarCampo("CPF (11 dígitos):", panel, gbc, 1);
        txtTelefone = criarCampo("Telefone:", panel, gbc, 2);
        txtDataNasc = criarCampo("Data de Nascimento (dd/MM/yyyy):", panel, gbc, 3);
        txtEndereco = criarCampo("Endereço:", panel, gbc, 4);
        txtSaldo = criarCampo("Saldo inicial (R$):", panel, gbc, 5);

        // Botão cadastrar
        JButton btnCadastrar = new JButton("✅ Cadastrar Cliente");
        btnCadastrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCadastrar.setBackground(new Color(0, 102, 51));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFocusPainted(false);
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 5, 5);
        panel.add(btnCadastrar, gbc);

        // Linha divisória
        JSeparator separator = new JSeparator();
        gbc.gridy = 7; gbc.insets = new Insets(20, 5, 10, 5);
        panel.add(separator, gbc);

        // Busca por CPF
        JLabel lblBusca = new JLabel("🔍 Buscar por CPF:");
        lblBusca.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridy = 8; gbc.insets = new Insets(10, 5, 5, 5);
        panel.add(lblBusca, gbc);

        txtBuscaCpf = new JTextField(15);
        gbc.gridy = 9;
        panel.add(txtBuscaCpf, gbc);

        JButton btnBuscar = new JButton("🔎 Buscar");
        btnBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridy = 10;
        panel.add(btnBuscar, gbc);

        // Ações
        btnCadastrar.addActionListener(this::acaoCadastrar);
        btnBuscar.addActionListener(this::acaoBuscar);

        return panel;
    }

    private JTextField criarCampo(String rotulo, JPanel panel, GridBagConstraints gbc, int linha) {
        JLabel lbl = new JLabel(rotulo);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(lbl, gbc);

        JTextField field = new JTextField(20);
        gbc.gridx = 1;
        panel.add(field, gbc);
        return field;
    }

    private JPanel criarPainelTabela() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("📋 Clientes Cadastrados"));

        // Modelo da tabela
        String[] colunas = {"Nome", "CPF", "Telefone", "Saldo"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaClientes = new JTable(modeloTabela);
        tabelaClientes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabelaClientes.setRowHeight(25);
        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Estilo da tabela
        tabelaClientes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabelaClientes.getTableHeader().setBackground(new Color(220, 235, 255));
        tabelaClientes.setFillsViewportHeight(true);

        JScrollPane scroll = new JScrollPane(tabelaClientes);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private void carregarClientesNaTabela() {
        modeloTabela.setRowCount(0); // limpa

    }

    private void acaoCadastrar(ActionEvent e) {
        try {
            String nome = txtNome.getText().trim();
            String cpf = txtCpf.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String dataStr = txtDataNasc.getText().trim();
            String endereco = txtEndereco.getText().trim();
            String saldoStr = txtSaldo.getText().trim();

            // Validações básicas
            if (nome.isEmpty() || cpf.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Nome e CPF são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cpf.length() != 11 || !cpf.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "❌ CPF deve ter 11 dígitos numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (saldoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Saldo é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate dataNasc = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            double saldo = Double.parseDouble(saldoStr.replace(",", "."));

            Cliente cliente = new Cliente(nome, cpf, saldo, telefone, dataNasc, endereco);
            banco.adicionarCliente(cliente);

            // Limpar campos
            txtNome.setText("");
            txtCpf.setText("");
            txtTelefone.setText("");
            txtDataNasc.setText("");
            txtEndereco.setText("");
            txtSaldo.setText("");
            txtNome.requestFocus();

            // Atualizar tabela (simulando — na prática, adicione getClientes())
            modeloTabela.addRow(new Object[]{
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                String.format("R$ %.2f", cliente.getSaldo())
            });

            JOptionPane.showMessageDialog(this, "✅ Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "❌ Data inválida. Use dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "❌ Saldo deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoBuscar(ActionEvent e) {
        String cpf = txtBuscaCpf.getText().trim();
        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "🔍 Digite um CPF para buscar.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente c = banco.buscarPorCpf(cpf);
        if (c != null) {
            // Exibir em diálogo formatado
            String info = String.format(
                "<html><h3>✅ Cliente Encontrado</h3>" +
                "<b>📇 Nome:</b> %s<br>" +
                "<b>🪪 CPF:</b> %s<br>" +
                "<b>📞 Telefone:</b> %s<br>" +
                "<b>🎂 Nascimento:</b> %s<br>" +
                "<b>🏠 Endereço:</b> %s<br>" +
                "<b>💰 Saldo:</b> R$ %.2f</html>",
                c.getNome(),
                c.getCpf(),
                c.getTelefone(),
                c.getDataNascimento() != null ? 
                    c.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : 
                    "não informada",
                c.getEndereco(),
                c.getSaldo()
            );
            JOptionPane.showMessageDialog(this, info, "Informações do Cliente", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "❌ Cliente não encontrado para o CPF: " + cpf, "Não encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Método para atualizar a tabela externamente (ex: após carregar do console)
    public void atualizarTabela() {
        modeloTabela.setRowCount(0);
    }

    public static void main(String[] args) {
        try {
            // LookAndFeel moderno
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            Banco banco = new Banco();

            // Pré-cadastro para teste visual
            banco.adicionarCliente(new Cliente(
                "João Silva", "12345678901", 1500.00,
                "(11) 98765-4321",
                LocalDate.of(1990, 5, 15),
                "Rua A, 123 - Centro, SP"
            ));
            banco.adicionarCliente(new Cliente(
                "Maria Oliveira", "11122233344", 2800.50,
                "(21) 91234-5678",
                LocalDate.of(1988, 12, 3),
                "Av. B, 456 - Copacabana, RJ"
            ));

            TelaPrincipal tela = new TelaPrincipal(banco);
            tela.setVisible(true);
        });
    }
}