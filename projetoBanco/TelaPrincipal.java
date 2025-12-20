package projetoBanco;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@SuppressWarnings("preview")
public class TelaPrincipal extends JFrame {
    private Banco banco;
    private JTable tabelaClientes;
    private DefaultTableModel modeloTabela;
    private JTextField txtNome, txtCpf, txtTelefone, txtDataNasc, txtEndereco, txtSaldo;
    private JTextField txtBuscaCpf;
    private JButton btnCadastrarPrincipal; // Campo da classe

    private static final Color PRIMARY = new Color(0, 82, 147);
    private static final Color SUCCESS = new Color(25, 135, 84);
    private static final Color DANGER = new Color(220, 53, 69);
    private static final Color LIGHT_BG = new Color(248, 250, 252);
    private static final Color CARD_BG = Color.WHITE;

    public TelaPrincipal(Banco banco) {
        this.banco = banco;
        configurarLookAndFeel();
        configurarJanela();
        montarInterface();
    }

    private void configurarLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    UIManager.put("nimbusBase", PRIMARY);
                    UIManager.put("nimbusBlueGrey", LIGHT_BG);
                    UIManager.put("control", LIGHT_BG);
                    break;
                }
            }
        } catch (Exception ignored) {}
    }

    private void configurarJanela() {
        setTitle(" Super Banco • Gestão de Clientes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 720);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 248, 252));
        setLayout(new BorderLayout(15, 15));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void montarInterface() {
        JLabel lblTitulo = new JLabel("SUPER BANCO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(PRIMARY);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(lblTitulo, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(480);
        splitPane.setResizeWeight(0.45);
        splitPane.setBorder(BorderFactory.createEmptyBorder());

        splitPane.setLeftComponent(criarPainelFormulario());
        splitPane.setRightComponent(criarPainelTabela());

        add(splitPane, BorderLayout.CENTER);
        carregarClientesNaTabela();
    }

    private JPanel criarCard(String titulo) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 20));
                g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 22, 22);
                g2.setColor(CARD_BG);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 22, 22);
                g2.dispose();
            }
        };
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(25, 25, 25, 25),
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 230, 245), 1, true),
                titulo,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 15),
                new Color(60, 70, 90)
            )
        ));
        return card;
    }

    private JButton criarBotao(String texto, Color corFundo) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(corFundo);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(false);

        Color hoverColor = corFundo.darker();
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(corFundo);
            }
        });

        return btn;
    }

    private JTextField criarCampoModerno() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 230, 245), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setBackground(Color.WHITE);
        field.setForeground(new Color(30, 40, 50));
        return field;
    }

    private JScrollPane criarPainelFormulario() {
        JPanel card = criarCard("Cadastro de Cliente");
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 0, 1, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        java.util.function.BiFunction<String, Integer, Void> criarLinha = (rotulo, y) -> {
            JLabel lbl = new JLabel(rotulo);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lbl.setForeground(new Color(50, 60, 70));
            lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
            gbc.gridx = 0; gbc.gridy = y; gbc.anchor = GridBagConstraints.WEST;
            form.add(lbl, gbc);

            JTextField field = criarCampoModerno();
            gbc.gridx = 1; gbc.weightx = 1.0;
            form.add(field, gbc);

            if (rotulo.contains("Nome")) txtNome = field;
            else if (rotulo.contains("CPF")) txtCpf = field;
            else if (rotulo.contains("Telefone")) txtTelefone = field;
            else if (rotulo.contains("Nascimento")) txtDataNasc = field;
            else if (rotulo.contains("Endereço")) txtEndereco = field;
            else if (rotulo.contains("Saldo")) txtSaldo = field;

            return null;
        };

        criarLinha.apply(" Nome completo:", 0);
        criarLinha.apply(" CPF (11 dígitos):", 1);
        criarLinha.apply(" Telefone:", 2);
        criarLinha.apply(" Data de Nascimento (dd/MM/yyyy):", 3);
        criarLinha.apply(" Endereço:", 4);
        criarLinha.apply(" Saldo inicial (R$):", 5);

        btnCadastrarPrincipal = criarBotao("✅ Cadastrar Cliente", SUCCESS); // ✅ campo da classe
        JButton btnCadastrar = btnCadastrarPrincipal;
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; gbc.insets = new Insets(25, 0, 10, 0);
        form.add(btnCadastrar, gbc);
        btnCadastrar.addActionListener(this::acaoCadastrar);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(220, 230, 245));
        gbc.gridy = 7; gbc.insets = new Insets(30, 0, 15, 0);
        form.add(sep, gbc);

        JLabel lblBusca = new JLabel("Buscar cliente por CPF:");
        lblBusca.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblBusca.setForeground(new Color(50, 60, 70));
        gbc.gridy = 8; gbc.insets = new Insets(10, 0, 8, 0);
        form.add(lblBusca, gbc);

        txtBuscaCpf = criarCampoModerno();
        gbc.gridy = 9; gbc.insets = new Insets(0, 0, 15, 0);
        form.add(txtBuscaCpf, gbc);

        JButton btnBuscar = criarBotao("🔎 Buscar", PRIMARY);
        gbc.gridy = 10; gbc.insets = new Insets(5, 0, 0, 0);
        form.add(btnBuscar, gbc);
        btnBuscar.addActionListener(this::acaoBuscar);

        card.add(form, BorderLayout.CENTER);
        return new JScrollPane(card) {{
            setBorder(BorderFactory.createEmptyBorder());
            getViewport().setOpaque(false);
            setOpaque(false);
        }};
    }

    private JPanel criarPainelTabela() {
        JPanel card = criarCard("Clientes Cadastrados");

        String[] colunas = {"Nome", "CPF", "Telefone", "Saldo"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tabelaClientes = new JTable(modeloTabela) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 248, 252));
                } else {
                    c.setBackground(new Color(200, 220, 245));
                }
                return c;
            }
        };

        tabelaClientes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelaClientes.setRowHeight(42);
        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaClientes.setShowGrid(false);
        tabelaClientes.setIntercellSpacing(new Dimension(0, 0));
        tabelaClientes.setFillsViewportHeight(true);

        JTableHeader header = tabelaClientes.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(245, 248, 252));
        header.setForeground(new Color(60, 70, 90));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 230, 245)));

        JScrollPane scroll = new JScrollPane(tabelaClientes) {{
            setBorder(BorderFactory.createEmptyBorder());
            getViewport().setBackground(Color.WHITE);
        }};

        card.add(scroll, BorderLayout.CENTER);

        JButton btnRemover = criarBotao("🗑️ Remover Cliente", DANGER);
        btnRemover.addActionListener(e -> {
            int row = tabelaClientes.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(TelaPrincipal.this,
                    "<html><b>⚠️ Selecione um cliente</b><br>Clique em um cliente para remover.</html>",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            String cpf = (String) modeloTabela.getValueAt(row, 1);
            int resp = JOptionPane.showConfirmDialog(TelaPrincipal.this,
                "<html><b>❓ Confirmar exclusão?</b><br>Cliente com CPF: <b>" + cpf + "</b></html>",
                "Remover Cliente",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

            if (resp == JOptionPane.YES_OPTION && banco.removerClientePorCpf(cpf.replaceAll("[^\\d]", ""))) {
                modeloTabela.removeRow(row);
                JOptionPane.showMessageDialog(TelaPrincipal.this,
                    "<html><b>✅ Cliente removido!</b><br>Operação concluída com sucesso.</html>",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            } else if (resp == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(TelaPrincipal.this,
                    "<html><b>❌ Cliente não encontrado.</b></html>",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setOpaque(false);
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        painelBotoes.add(btnRemover);

        JButton btnEditar = criarBotao("✏️ Editar Cliente", PRIMARY);
        btnEditar.addActionListener(this::acaoEditar); 
        painelBotoes.add(btnEditar);

        card.add(painelBotoes, BorderLayout.SOUTH);

        return card;
    }

    private void carregarClientesNaTabela() {
        modeloTabela.setRowCount(0);
        for (Cliente c : banco.getClientes()) {
            modeloTabela.addRow(new Object[]{
                c.getNome(),
                formatarCpf(c.getCpf()),
                c.getTelefone(),
                String.format("R$ %.2f", c.getSaldo())
            });
        }
    }

    private String formatarCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) return cpf;
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    private void acaoCadastrar(ActionEvent e) {
        try {
            String nome = txtNome.getText().trim();
            String cpf = txtCpf.getText().trim().replaceAll("[^\\d]", "");
            String telefone = txtTelefone.getText().trim();
            String dataStr = txtDataNasc.getText().trim();
            String endereco = txtEndereco.getText().trim();
            String saldoStr = txtSaldo.getText().trim();

            // ✅ Não declare btnCadastrarPrincipal aqui! Já é campo da classe.

            if (nome.isEmpty() || cpf.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "<html><b>❌ Campos obrigatórios</b><br>Preencha <b>Nome</b> e <b>CPF</b>.</html>",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cpf.length() != 11) {
                JOptionPane.showMessageDialog(this,
                    "<html><b>❌ CPF inválido</b><br>Informe 11 dígitos numéricos.</html>",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (saldoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "<html><b>❌ Saldo obrigatório</b><br>Informe o valor inicial.</html>",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (banco.buscarPorCpf(cpf) != null) {
                JOptionPane.showMessageDialog(this,
                    "<html><b>⚠️ CPF já cadastrado!</b><br>Verifique se o cliente já existe.<br>" +
                    "<small>CPF: " + formatarCpf(cpf) + "</small></html>",
                    "CPF Duplicado",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            LocalDate dataNasc = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            double saldo = Double.parseDouble(saldoStr.replace(",", "."));

            Cliente cliente = new Cliente(nome, cpf, saldo, telefone, dataNasc, endereco);
            banco.adicionarCliente(cliente);

            modeloTabela.addRow(new Object[]{
                cliente.getNome(),
                formatarCpf(cliente.getCpf()),
                cliente.getTelefone(),
                String.format("R$ %.2f", cliente.getSaldo())
            });

            limparCampos(); // ✅ reutilizado
            JOptionPane.showMessageDialog(this,
                "<html><b>✅ Cliente cadastrado!</b><br>" + nome + " adicionado com sucesso.</html>",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                "<html><b>❌ Formato de data inválido</b><br>Use <b>dd/MM/yyyy</b>.</html>",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "<html><b>❌ Saldo inválido</b><br>Ex: <b>1500.50</b></html>",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "<html><b>❌ Erro</b><br>" + ex.getMessage() + "</html>",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoBuscar(ActionEvent e) {
        String cpf = txtBuscaCpf.getText().trim().replaceAll("[^\\d]", "");
        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "🔍 Digite um CPF para buscar.",
                "Atenção",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente c = banco.buscarPorCpf(cpf);
        if (c != null) {
            String info = String.format(
                "<html><div style='padding:15px; line-height:1.5'>" +
                "<h3 style='color:#1a56db; margin:0 0 12px 0;'>✅ Cliente Encontrado</h3>" +
                "<p><b>📇 Nome:</b> %s</p>" +
                "<p><b>🪪 CPF:</b> %s</p>" +
                "<p><b>📞 Telefone:</b> %s</p>" +
                "<p><b>🎂 Nascimento:</b> %s</p>" +
                "<p><b>🏠 Endereço:</b> %s</p>" +
                "<p><b>💰 Saldo:</b> <span style='color:#10b981'>R$ %.2f</span></p>" +
                "</div></html>",
                c.getNome(),
                formatarCpf(c.getCpf()),
                c.getTelefone(),
                c.getDataNascimento() != null ?
                    c.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "não informada",
                c.getEndereco(),
                c.getSaldo()
            );
            JOptionPane.showMessageDialog(this, info, "Informações do Cliente", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "<html><b>❌ Não encontrado</b><br>Nenhum cliente com CPF:<br><b>" + formatarCpf(cpf) + "</b></html>",
                "Não Encontrado",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    // ✅ Método de edição — fora de qualquer outro método!
    private void acaoEditar(ActionEvent e) {
        int row = tabelaClientes.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                "<html><b>⚠️ Selecione um cliente</b><br>Clique em um cliente para editar.</html>",
                "Atenção",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cpfFormatado = (String) modeloTabela.getValueAt(row, 1);
        String cpf = cpfFormatado.replaceAll("[^\\d]", "");

        Cliente cliente = banco.buscarPorCpf(cpf);
        if (cliente == null) {
            JOptionPane.showMessageDialog(this,
                "<html><b>❌ Cliente não encontrado.</b><br>Tente novamente.</html>",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Preencher campos com dados do cliente
        txtNome.setText(cliente.getNome());
        txtCpf.setText(formatarCpf(cliente.getCpf()));
        txtTelefone.setText(cliente.getTelefone());
        txtDataNasc.setText(
            cliente.getDataNascimento() != null ?
                cliente.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : ""
        );
        txtEndereco.setText(cliente.getEndereco());
        txtSaldo.setText(String.format("%.2f", cliente.getSaldo()));

        // Mudar título da janela
        setTitle(" Super Banco • Editando: " + cliente.getNome());

        // Alterar botão para "Salvar Edição"
        btnCadastrarPrincipal.setText("💾 Salvar Alterações");
        btnCadastrarPrincipal.setBackground(new Color(255, 193, 7)); // amarelo suave

        // Remover antigo listener e adicionar novo
        for (ActionListener al : btnCadastrarPrincipal.getActionListeners()) {
            btnCadastrarPrincipal.removeActionListener(al);
        }
        btnCadastrarPrincipal.addActionListener(ev -> salvarEdicao(cliente, row));
    }

    // ✅ Salvar edição
    private void salvarEdicao(Cliente clienteOriginal, int rowIndex) {
        try {
            String nome = txtNome.getText().trim();
            String cpfDigitado = txtCpf.getText().trim().replaceAll("[^\\d]", "");
            String telefone = txtTelefone.getText().trim();
            String dataStr = txtDataNasc.getText().trim();
            String endereco = txtEndereco.getText().trim();
            String saldoStr = txtSaldo.getText().trim();

            // Validações
            if (nome.isEmpty() || cpfDigitado.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "<html><b>❌ Campos obrigatórios</b><br>Preencha <b>Nome</b> e <b>CPF</b>.</html>",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cpfDigitado.length() != 11) {
                JOptionPane.showMessageDialog(this,
                    "<html><b>❌ CPF inválido</b><br>Informe 11 dígitos numéricos.</html>",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (saldoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "<html><b>❌ Saldo obrigatório</b><br>Informe o valor inicial.</html>",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ✅ Permite CPF igual ao atual, mas não outro já cadastrado
            Cliente clienteComMesmoCpf = banco.buscarPorCpf(cpfDigitado);
            if (clienteComMesmoCpf != null &&
                !clienteComMesmoCpf.getCpf().equals(clienteOriginal.getCpf())) {
                JOptionPane.showMessageDialog(this,
                    "<html><b>⚠️ CPF já cadastrado!</b><br>Outro cliente já usa este CPF.<br>" +
                    "<small>CPF: " + formatarCpf(cpfDigitado) + "</small></html>",
                    "CPF Duplicado",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            LocalDate dataNasc = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            double saldo = Double.parseDouble(saldoStr.replace(",", "."));

            // Atualizar cliente
            clienteOriginal.setNome(nome);
            clienteOriginal.setCpf(cpfDigitado);
            clienteOriginal.setTelefone(telefone);
            clienteOriginal.setDataNascimento(dataNasc);
            clienteOriginal.setEndereco(endereco);
            clienteOriginal.setSaldo(saldo);

            // Atualizar tabela
            modeloTabela.setValueAt(nome, rowIndex, 0);
            modeloTabela.setValueAt(formatarCpf(cpfDigitado), rowIndex, 1);
            modeloTabela.setValueAt(telefone, rowIndex, 2);
            modeloTabela.setValueAt(String.format("R$ %.2f", saldo), rowIndex, 3);

            // Limpar e restaurar
            limparCampos();
            restaurarBotaoCadastrar();

            JOptionPane.showMessageDialog(this,
                "<html><b>✅ Cliente atualizado!</b><br>Dados de <b>" + nome + "</b> foram salvos.</html>",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                "<html><b>❌ Formato de data inválido</b><br>Use <b>dd/MM/yyyy</b>.</html>",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "<html><b>❌ Saldo inválido</b><br>Ex: <b>1500.50</b></html>",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "<html><b>❌ Erro</b><br>" + ex.getMessage() + "</html>",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtDataNasc.setText("");
        txtEndereco.setText("");
        txtSaldo.setText("");
        txtNome.requestFocus();
    }

    private void restaurarBotaoCadastrar() {
        btnCadastrarPrincipal.setText("✅ Cadastrar Cliente");
        btnCadastrarPrincipal.setBackground(SUCCESS);
        // Remover listener de edição
        for (ActionListener al : btnCadastrarPrincipal.getActionListeners()) {
            btnCadastrarPrincipal.removeActionListener(al);
        }
        // Restaurar listener original
        btnCadastrarPrincipal.addActionListener(this::acaoCadastrar);
        setTitle(" Super Banco • Gestão de Clientes");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Banco banco = new Banco();
            new TelaPrincipal(banco).setVisible(true);
        });
    }
}