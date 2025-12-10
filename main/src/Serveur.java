import java.io.*;
import java.net.*;


public class Serveur{
        public static void main(String[] args){
                try{
                        ServerSocket conn= new ServerSocket(7777);
                        System.out.println("Serveur Connecté");
                        Socket comm=conn.accept();
                        System.out.println("Client Connecté...");
                        PrintWriter Out =new PrintWriter(comm.getOutputStream)



                }




}}
