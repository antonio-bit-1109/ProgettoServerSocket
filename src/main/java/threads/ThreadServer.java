package threads;

import fileManager.HandleFile;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;


public class ThreadServer implements Runnable {

    private Socket socket;
    private int IdServer;
    private HandleFile handleFileClass;
    private ArrayList<Character> charList;
    private String inputClientMsg;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setInputClientMsg(String inputClientMsg) {
        this.inputClientMsg = inputClientMsg;
    }

    public void setIdServer(int idServer) {
        IdServer = idServer;
    }

    public void setHandleFileClass(HandleFile handleFileClass) {
        this.handleFileClass = handleFileClass;
    }

    public void setCharList(ArrayList<Character> charList) {
        this.charList = charList;
    }

    public String getInputClientMsg() {
        return inputClientMsg;
    }

    public void AddToCharlist(char... c) {
        for (int i = 0; i < c.length; i++) {
            this.getCharList().add(c[i]);
        }
    }

    //costrutt
    public ThreadServer(Socket socket, int id, HandleFile handlefile) {
        setSocket(socket);
        setIdServer(id);
        setHandleFileClass(handlefile);
    }

    public int getIdServer() {
        return IdServer;
    }

    public Socket getSocket() {
        return socket;
    }

    public ArrayList<Character> getCharList() {
        return charList;
    }

    public HandleFile getHandleFileClass() {
        return handleFileClass;
    }

    @Override
    public void run() {

        setCharList(new ArrayList<>());
        AddToCharlist('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'j', 'k', ' ', '?', '!', ':');

        try (BufferedReader dataInput = new BufferedReader(new InputStreamReader(getSocket().getInputStream()))) {
            PrintWriter outputServer = new PrintWriter(System.out, true);

            setInputClientMsg("");
            String StringRawFormat;

            while ((StringRawFormat = dataInput.readLine()) != null) {

                char[] StringRawArr = StringRawFormat.toCharArray();

                for (int i = 0; i < StringRawArr.length; i++) {
                    char l = StringRawArr[i];

                    if (charList.contains(l)) {
                        setInputClientMsg(getInputClientMsg() + l);
                        //inputClientMsg += l;
                    }
                }

                outputServer.println("Message received from client: N° " + getIdServer() + " Message:  " + inputClientMsg);
                handleFileClass.writeOnFile(inputClientMsg, getIdServer());
                setInputClientMsg("");
            }
            //  inputClientMsg = "";
        } catch (IOException ex) {
            System.out.println("errore comunicazione Client - Server");
        }
    }

}
