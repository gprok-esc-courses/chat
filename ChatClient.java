import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ChatClient {

    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private String name;

    public ChatClient(ThreadClient thread, String name, DefaultListModel<String> messages, ChatClientGUI.JoinPanel gui) {
        this.name = name;
        try {
            InetAddress address = InetAddress.getByName(Configuration.SERVER_IP_ADDRESS);
            socket = new Socket(address, Configuration.PORT);


            in = new BufferedReader(
                    new InputStreamReader(
                        socket.getInputStream()));

            out = new PrintWriter(
                     new BufferedWriter(
                        new OutputStreamWriter(
                            socket.getOutputStream())),true);

            connectMessage();

            thread = new ThreadClient(in, messages, gui, this);
            thread.start();



        } catch (IOException e) {
            System.out.println("Not connected");
        }
    }

    public void close() {
        System.out.println("closing...");
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void connectMessage() {
        JSONObject json = new JSONObject();
        json.put("type", "connection");
        json.put("user", name);
        out.println(json.toString());
    }


    public void sendMessage(String message) {
        JSONObject json = new JSONObject();
        json.put("type", "message");
        json.put("user", name);
        json.put("value", message);
        out.println(json.toString());
    }


}
