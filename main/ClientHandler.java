package main;

import java.net.*;
import java.io.*;

public class ClientHandler extends Thread {

    private Socket socket;
    private Serveur server;
    private BufferedReader in;
    private PrintWriter out;
    private String pseudo;

    public ClientHandler(Socket socket, Serveur server) {
        this.socket = socket;
        this.server = server;
    }

    public void envoieMessage(String msg) {
        out.println(msg);
    }

    @Override
    public void run() {
        try {
            in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            pseudo = in.readLine();
            server.broadcast( pseudo + " s'est connecté");

            String msg;
            while ((msg = in.readLine()) != null) {
                server.broadcast(pseudo + " : " + msg);
            }

        } catch (IOException e) {
            System.out.println("Client déconnecté");
        } finally {
            server.supprimerClient(this);
            server.broadcast( pseudo + " s'est déconnecté");
            try { socket.close(); } catch (IOException ignored) {}
        }
    }
}
