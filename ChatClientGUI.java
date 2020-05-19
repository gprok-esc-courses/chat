import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatClientGUI extends JFrame {

    JoinPanel joinPanel;
    WritePanel writePanel;
    ChatClient client;
    ThreadClient thread;

    private JTextField nameField;
    private JTextField messageField;
    private DefaultListModel<String> messages;
    private JList messageList;

    public ChatClientGUI() {
        this.setSize(400,800);
        this.setTitle("Skype Clone");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        joinPanel = new JoinPanel();
        this.add(joinPanel, BorderLayout.NORTH);

        writePanel = new WritePanel();
        this.add(writePanel, BorderLayout.SOUTH);

        messages = new DefaultListModel<>();
        messages.addElement("test");
        messages.addElement("dummy");
        messageList = new JList(messages);
        JScrollPane scroll = new JScrollPane(messageList);
        this.add(scroll);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                String msg = "LEAVING ...";
                client.sendMessage(msg);
                System.exit(0);
            }
        });

        this.setVisible(true);
    }




    class JoinPanel extends JPanel {

        JLabel nameLabel;
        JButton join;
        boolean joined;

        public JoinPanel() {
            joined = false;
            nameLabel = new JLabel("Name");
            nameField = new JTextField(20);
            nameField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    int n = nameField.getText().length();
                    join.setEnabled(n > 0 ? true : false);
                }
            });
            join = new JButton("Join");
            join.setEnabled(false);

            join.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameField.getText();
                    if(joined) {
                        client.sendMessage("LEAVING ...");
                        System.exit(0);
                    }
                    else {
                        client = new ChatClient(thread, name, messages, JoinPanel.this);
                    }
                }
            });

            this.add(nameLabel);
            this.add(nameField);
            this.add(join);
        }

        public void setJoined() {
            joined = true;
            nameField.setEditable(false);
            join.setText("Quit");
        }
    }

    class WritePanel extends JPanel {

        JButton send;

        public WritePanel() {
            messageField = new JTextField(25);
            messageField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    int n = nameField.getText().length();
                    send.setEnabled(n > 0 ? true : false);
                }
            });
            send = new JButton("Send");
            send.setEnabled(false);

            send.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String msg = messageField.getText();
                    messages.addElement(msg);
                    client.sendMessage(msg);
                }
            });

            this.add(messageField);
            this.add(send);
        }

    }


    public static void main(String[] args) {
        ChatClientGUI gui = new ChatClientGUI();
    }

}
