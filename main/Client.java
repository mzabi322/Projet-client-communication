package main;

import java.net.*;
import java.io.*;

public class Client {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Client(String host, int port, String pseudo) throws IOException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        out.println(pseudo);
    }

    public void envoieMessage(String msg) {
        out.println(msg);
    }

    public String recoieMessage() throws IOException {
        return in.readLine();
    }

    public void fermer() throws IOException {
        socket.close();
    }
}
