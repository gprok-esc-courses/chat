import org.json.JSONObject;

import java.io.*;
import java.net.*;

public class ThreadServer implements Runnable
{
    private Thread chatThread;
    private ClientRepository clients;
    private String name;

    public BufferedReader in;


    public ThreadServer(BufferedReader i, ClientRepository c, String user)
    {
        in = i;
        clients = c;
        name = user;
    }

    public void init() {
        chatThread = null;
    }

    public void run()
    {
        try {

            while (true) {
                try {
                    String str = in.readLine();
                    JSONObject json = new JSONObject(str);
                    clients.messageAll(json.getString("value"), json.getString("user"));
                    if (json.getString("value").equals("LEAVING ...")) {
                        System.out.println("User " + json.getString("user") + " leaving");
                        clients.removeClient(name);
                        break;
                    }
                }
                catch (NullPointerException e) {
                    System.out.println("Cannot read from client " + name);
                    break;
                }
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
