package br.com.unifecaf.view;

import br.com.unifecaf.controller.ClientController;
import br.com.unifecaf.model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ViewCreateClient extends JFrame {

    private final JTextField txtName;
    private final JTextField txtCPF;
    private final JTextField txtRG;
    private final JTextField txtEmail;
    private final JTextField txtIdade;
    private final JTextArea  txtLog;

    private final ClientController controller;

    public ViewCreateClient(ClientController controller) {
        super("Cadastro de Cliente");
        this.controller = controller;

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel formPanel = new JPanel(new GridBagLayout());
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        txtName  = new JTextField(22);
        txtCPF   = new JTextField(14);
        txtRG    = new JTextField(12);
        txtEmail = new JTextField(22);
        txtIdade = new JTextField(6);

        txtLog = new JTextArea(8, 48);
        txtLog.setEditable(false);
        txtLog.setLineWrap(true);
        txtLog.setWrapStyleWord(true);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Nome
        c.gridx = 0; c.gridy = 0; c.weightx = 0; formPanel.add(new JLabel("Nome:"), c);
        c.gridx = 1; c.gridy = 0; c.weightx = 1; formPanel.add(txtName, c);

        // CPF
        c.gridx = 0; c.gridy = 1; c.weightx = 0; formPanel.add(new JLabel("CPF:"), c);
        c.gridx = 1; c.gridy = 1; c.weightx = 1; formPanel.add(txtCPF, c);

        // RG
        c.gridx = 0; c.gridy = 2; c.weightx = 0; formPanel.add(new JLabel("RG:"), c);
        c.gridx = 1; c.gridy = 2; c.weightx = 1; formPanel.add(txtRG, c);

        // Email
        c.gridx = 0; c.gridy = 3; c.weightx = 0; formPanel.add(new JLabel("Email:"), c);
        c.gridx = 1; c.gridy = 3; c.weightx = 1; formPanel.add(txtEmail, c);

        // Idade
        c.gridx = 0; c.gridy = 4; c.weightx = 0; formPanel.add(new JLabel("Idade:"), c);
        c.gridx = 1; c.gridy = 4; c.weightx = 1; formPanel.add(txtIdade, c);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnBuscar = new JButton("Buscar por CPF");
        JButton btnLimpar = new JButton("Limpar");

        btnSalvar.addActionListener(this::onSalvar);
        btnBuscar.addActionListener(this::onBuscar);
        btnLimpar.addActionListener(e -> clearForm());

        actionsPanel.add(btnLimpar);
        actionsPanel.add(btnBuscar);
        actionsPanel.add(btnSalvar);

        JScrollPane logScroll = new JScrollPane(
                txtLog,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        logScroll.setBorder(BorderFactory.createTitledBorder("Log"));

        root.add(formPanel, BorderLayout.NORTH);
        root.add(actionsPanel, BorderLayout.CENTER);
        root.add(logScroll, BorderLayout.SOUTH);

        setContentPane(root);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    // ===== Ações da View (sem regras de negócio) =====

    private void onSalvar(ActionEvent e) {
        try {
            // View só coleta como String e repassa
            String name  = txtName.getText();
            String cpf   = txtCPF.getText();
            String rg    = txtRG.getText();
            String email = txtEmail.getText();
            String idade = txtIdade.getText();

            Client c = controller.addClient(name, cpf, rg, email, idade);

            txtLog.append(String.format("✔ Cliente salvo: %s (CPF: %s)%n", c.getName(), c.getCPF()));
            clearForm();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onBuscar(ActionEvent e) {
        String cpf = txtCPF.getText();
        if (cpf == null || cpf.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o CPF para buscar.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Client c = controller.searchClient(cpf);
        if (c == null) {
            txtLog.append(String.format("✖ Cliente não encontrado para CPF: %s%n", cpf));
            JOptionPane.showMessageDialog(this, "Cliente não encontrado.", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        txtName.setText(c.getName());
        txtRG.setText(c.getRG());
        txtEmail.setText(c.getEmail());
        txtIdade.setText(String.valueOf(c.getIdade()));

        txtLog.append(String.format("ℹ Cliente carregado: %s (CPF: %s)%n", c.getName(), c.getCPF()));
    }

    private void clearForm() {
        txtName.setText("");
        txtCPF.setText("");
        txtRG.setText("");
        txtEmail.setText("");
        txtIdade.setText("");
        txtName.requestFocus();
    }

    // Main de teste manual (opcional)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClientController controller = new ClientController();
            new ViewCreateClient(controller).setVisible(true);
        });
    }
}
