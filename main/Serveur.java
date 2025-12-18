package main;

import java.net.*;
import java.io.*;
import java.util.*;


public class Serveur {
    private ServerSocket serverSocket;
    private final ArrayList<ClientHandler> clients = new ArrayList<>();

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Serveur démarré sur le port " + port);

        while (true) {
            Socket socket = serverSocket.accept();
            ClientHandler client = new ClientHandler(socket, this);
            clients.add(client);
            client.start();
        }
    }

    public void broadcast(String message) {
        for (ClientHandler c : clients) {
            c.envoieMessage(message);
        }
    }

    public void supprimerClient(ClientHandler client) {
        clients.remove(client);
    }

    public static void main(String[] args) throws IOException {
        new Serveur().start(5000);
    }


}