import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Serveur {

    static ArrayList<GestionClient> clients = new ArrayList<>();

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Serveur démarré sur le port 5000");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connecté");

                GestionClient client = new GestionClient(socket);
                clients.add(client);
                client.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Envoi à tous les clients
    public static void broadcast(String message, GestionClient sender) {
        for (GestionClient c : clients) {
            if (c != sender) {
                c.envoieMessage(message);
            }
        }
    }

    public static void supprimerClient(GestionClient client) {
        clients.remove(client);
    }
}
