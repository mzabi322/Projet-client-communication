package main;

import javax.swing.*;
import java.awt.*;

public class Interface {

    private Client client;
    private JTextArea area;
    private JTextField input;

    public Interface() {
        JFrame frame = new JFrame("Chat ");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        area = new JTextArea();
        area.setEditable(false);

        input = new JTextField();
        JButton send = new JButton("Envoyer");

        send.addActionListener(e -> {
            String msg = input.getText();
            if (!msg.isBlank()) {
                client.envoieMessage(msg);
                input.setText("");
            }
        });

        frame.add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(input, BorderLayout.CENTER);
        bottom.add(send, BorderLayout.EAST);

        frame.add(bottom, BorderLayout.SOUTH);
        frame.setVisible(true);

        connect();
    }

    private void connect() {
        String pseudo = JOptionPane.showInputDialog("Pseudo ?");
        try {
            client = new Client("localhost", 5000, pseudo);

            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = client.recoieMessage()) != null) {
                        area.append(msg + "\n");
                    }
                } catch (Exception ignored) {}
            }).start();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur connexion");
        }
    }


    public static void main(String[] args) {
        new Interface();
    }
}
