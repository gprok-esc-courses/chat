import org.json.JSONObject;

import java.util.ArrayList;

public class ClientRepository {

    private ArrayList<Client> clients;

    public ClientRepository() {
        clients = new ArrayList<>();
    }

    public boolean addClient(String name) {
        if(isOnline(name)) {
            return false;
        }
        Client client = new Client(name);
        clients.add(client);
        return true;
    }

    public boolean addClient(Client client) {
        if(isOnline(client.getName())) {
            return false;
        }
        clients.add(client);
        return true;
    }

    public boolean isOnline(String name) {
        for (Client client : clients) {
            if(client.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void messageAll(String msg, String user) {
        JSONObject json = new JSONObject();
        json.put("type", "message");
        json.put("user", user);
        json.put("value", msg);
        for(Client client : clients) {
            if(!client.getName().equals(user)) {
                client.getWriter().println(json.toString());
            }
        }
    }

    public void removeClient(String name) {
        System.out.println("repo removing " + name);
        for (Client client : clients) {
            if(client.getName().equals(name)) {
                clients.remove(client);
                break;
            }
        }
    }

    public ArrayList<Client> getAll() {
        return clients;
    }
}
