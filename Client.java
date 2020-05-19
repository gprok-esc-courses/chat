import java.io.BufferedReader;
import java.io.PrintWriter;

public class Client {

    private String name;
    private BufferedReader in;
    private PrintWriter out;

    public Client(String name) {
        this.name = name;
    }

    public Client(String name, BufferedReader in, PrintWriter out) {
        this.name = name;
        this.in = in;
        this.out = out;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BufferedReader getReader() {
        return in;
    }

    public void setReader(BufferedReader in) {
        this.in = in;
    }

    public PrintWriter getWriter() {
        return out;
    }

    public void setWriter(PrintWriter out) {
        this.out = out;
    }
}
