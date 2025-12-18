import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class ClientGUI extends JFrame {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private JTextArea zoneMessages;
    private JTextField champMessage;
    private JButton boutonEnvoyer;

    public ClientGUI() {
        setTitle("Chat INSA");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Zone d'affichage
        zoneMessages = new JTextArea();
        zoneMessages.setEditable(false);
        add(new JScrollPane(zoneMessages), BorderLayout.CENTER);

        // Zone de saisie
        champMessage = new JTextField();
        boutonEnvoyer = new JButton("Envoyer");

        JPanel panelBas = new JPanel(new BorderLayout());
        panelBas.add(champMessage, BorderLayout.CENTER);
        panelBas.add(boutonEnvoyer, BorderLayout.EAST);

        add(panelBas, BorderLayout.SOUTH);

        connexion();

        boutonEnvoyer.addActionListener(e -> envoyerMessage());
        champMessage.addActionListener(e -> envoyerMessage());

        setVisible(true);
    }

    private void connexion() {
        try {
            socket = new Socket("localhost", 5000);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String pseudo = JOptionPane.showInputDialog(
                    this, "Entrez votre pseudo :");

            out.println(pseudo);

            // Thread réception
            new Thread(() -> {
                String msg;
                try {
                    while ((msg = in.readLine()) != null) {
                        zoneMessages.append(msg + "\n");
                    }
                } catch (IOException e) {
                    zoneMessages.append("Déconnecté du serveur\n");
                }
            }).start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this, "Impossible de se connecter au serveur");
            System.exit(0);
        }
    }

    private void envoyerMessage() {
        String msg = champMessage.getText();

        if (!msg.isEmpty()) {

            // Affichage local
            zoneMessages.append("Moi : " + msg + "\n");

            // Envoi au serveur
            out.println(msg);
            champMessage.setText("");

            if (msg.equalsIgnoreCase("q")) {
                fermer();
            }
        }
    }

    private void fermer() {
        try {
            socket.close();
        } catch (IOException e) {
            // rien
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientGUI::new);
    }
}
