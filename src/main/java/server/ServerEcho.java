package server;

import fileManager.HandleFile;
import threads.ThreadServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerEcho {

    private static final int porta = 9999;
    private static int id = 1;

    public static int getPorta() {
        return porta;
    }

    public static void setId(int id) {
        ServerEcho.id = id;
    }

    public static int getId() {
        return id;
    }

    public static void main(String[] args) throws IOException {
        HandleFile handleFile = new HandleFile();

        // instanzio la calsse per gestione del file
        // la classe verrà passata ad ogni thread
        // che prendere l input in arrivo dall utente nel socket e lo stampa nel file.
        try (ServerSocket ServerSocket = new ServerSocket(getPorta())) {

            System.out.println("SERVER AVVIATO...");
            System.out.println("SERVER CHAT - IN ATTESA DI CONNESSIONE DA PARTE DI UN CLIENT.");

            while (true) {

                Socket clientSocket = ServerSocket.accept();
                System.out.println("connessione effettuata.");
                Thread t1 = new Thread(new ThreadServer(clientSocket, getId(), handleFile));
                setId(getId() + 1);
                t1.start();

            }


        } catch (IOException ex) {
            System.out.println("Errore durante la creazione del socket-server: " + ex);
        }


    }
}
