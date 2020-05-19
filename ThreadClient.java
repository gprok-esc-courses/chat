import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.net.*;

/**
 * Reads whatever comes from the server and displays on the JList
 */
public class ThreadClient implements Runnable
{
    private Thread chatThread;
    private ChatClientGUI.JoinPanel gui;
    private ChatClient client;

    private BufferedReader in;
    private DefaultListModel<String> messages;

    public ThreadClient(BufferedReader i, DefaultListModel<String> messages, ChatClientGUI.JoinPanel gui, ChatClient client)
    {
        in = i;

        this.messages = messages;
        this.gui = gui;
        this.client = client;
    }



    public void init() {
        chatThread = null;
    }

    public void run()
    {
        try {
            boolean stop = false;
            while (true) {
                String str = in.readLine();
                JSONObject msg = new JSONObject(str);
                switch (msg.getString("type")) {
                    case "join":
                        messages.addElement("WELCOME");
                        gui.setJoined();
                        break;
                    case "error":
                        messages.addElement("Error, " + msg.getString("value"));
                        client.close();
                        stop = true;
                        break;
                    case "message":
                        messages.addElement(msg.getString("user") + ": " + msg.getString("value"));
                }
                if(stop) break;
            }

        }
        catch(IOException e) {
            System.out.println(e);
        }
        finally {
            System.out.println("closing...");
        }
    }

    public void start()
    {
        if(chatThread == null) {
            chatThread = new Thread(this);
            chatThread.start();
        }
    }

}

