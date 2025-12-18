
import java.net.*;
import java.io.*;

public class GestionClient extends Thread {

    private Socket socket;
    private Serveur server;
    private BufferedReader in;
    private PrintWriter out;
    private String pseudo;

    public GestionClient(Socket socket) {
        this.socket = socket;

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
            Serveur.broadcast( pseudo + " s'est connecté",this);

            String msg;
            while ((msg = in.readLine()) != null) {

                if (msg.equalsIgnoreCase("q")) {
                    break;
                }
                Serveur.broadcast(pseudo + " : " + msg,this);
            }

        } catch (IOException e) {
            System.out.println("Client déconnecté");
        } finally {
            Serveur.supprimerClient(this);
            Serveur.broadcast( pseudo + " s'est déconnecté",this);
            try { socket.close(); } catch (IOException ignored) {}
        }
    }
}
