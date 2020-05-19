import org.json.JSONObject;

import java.io.*;
import java.net.*;

public class ChatServer {

    public static void main(String[] args) throws IOException {
        Socket socket;
        ServerSocket s = new ServerSocket(Configuration.PORT);
        System.out.println("Started: " + s);
        ClientRepository clients = new ClientRepository();

        try {
            while (true) {

                // Blocks until the first connection occurs:
                socket = s.accept();

                System.out.println("Connection accepted: " + socket);

                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()));

                PrintWriter out =
                        new PrintWriter(
                                new BufferedWriter(
                                        new OutputStreamWriter(
                                                socket.getOutputStream())), true);


                String input = in.readLine();
                System.out.println(input);
                JSONObject json = new JSONObject(input);
                String user = json.getString("user");
                Client client = new Client(user, in, out);

                json = new JSONObject();
                if(clients.addClient(client)) {
                    System.out.println(user + " connected");
                    json.put("type", "join");
                    json.put("value", "Connected");
                    out.println(json.toString());

                    new ThreadServer(in, clients, user).start();
                }
                else {
                    System.out.println(user + " duplicate");
                    json.put("type", "error");
                    json.put("value", "User already online");
                    out.println(json.toString());
                }

            }
        }
        finally {
            s.close();
        }
    }
}

