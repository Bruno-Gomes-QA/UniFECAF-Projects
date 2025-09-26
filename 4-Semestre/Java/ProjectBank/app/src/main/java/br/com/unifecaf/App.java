package br.com.unifecaf;

import javax.swing.SwingUtilities;

import br.com.unifecaf.controller.ClientController;
import br.com.unifecaf.view.ViewCreateClient;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Instancia o controller
            ClientController controller = new ClientController();

            // Passa o controller para a view
            ViewCreateClient view = new ViewCreateClient(controller);
            view.setVisible(true);
        });
    }
}
