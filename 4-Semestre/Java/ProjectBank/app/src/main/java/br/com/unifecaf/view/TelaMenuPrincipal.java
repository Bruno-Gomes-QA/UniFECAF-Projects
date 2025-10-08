
package br.com.unifecaf.view;


import br.com.unifecaf.controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class TelaMenuPrincipal extends JFrame {

    private JButton btnCadastrarCliente;
    private JButton btnLogin;
    private JButton btnSair;

    public TelaMenuPrincipal(ClientController client_controller) {

        setTitle("ProjectBank - Menu Inicial");
        setSize(450, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelPrincipal.setBackground(new Color(245, 245, 245));

        // Título
        JLabel lblTitulo = new JLabel("ProjectBank", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(52, 73, 94));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new GridLayout(3, 1, 15, 15));
        painelBotoes.setBackground(new Color(245, 245, 245));

        btnCadastrarCliente = new JButton("Cadastrar Cliente");
        btnLogin = new JButton("Login");
        btnSair = new JButton("Sair");

        // Estilo dos botões
        Color azul = new Color(52, 152, 219);
        Color vermelho = new Color(231, 76, 60);

        btnCadastrarCliente.setBackground(azul);
        btnLogin.setBackground(azul);
        btnSair.setBackground(vermelho);

        btnCadastrarCliente.setForeground(Color.WHITE);
        btnLogin.setForeground(Color.WHITE);
        btnSair.setForeground(Color.WHITE);

        btnCadastrarCliente.setFocusPainted(false);
        btnLogin.setFocusPainted(false);
        btnSair.setFocusPainted(false);

        painelBotoes.add(btnCadastrarCliente);
        painelBotoes.add(btnLogin);
        painelBotoes.add(btnSair);

        // Adiciona o painel de botões ao painel principal
        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);

        // Adiciona o painel principal ao JFrame
        add(painelPrincipal);

        // Ações dos botões

        btnCadastrarCliente.addActionListener(e -> new ViewCreateClient(client_controller).setVisible(true));
        //btnLogin.addActionListener(e -> new TelaLogin().setVisible(true));
        btnSair.addActionListener(e -> {
            int opcao = JOptionPane.showConfirmDialog(null,
                    "Deseja realmente sair?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION);
            if (opcao == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        setVisible(true);
    }
}
