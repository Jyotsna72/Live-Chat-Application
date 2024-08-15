import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private JFrame frame = new JFrame("Chat Client");
    private JTextField textField = new JTextField(40);
    private JTextArea messageArea = new JTextArea(20, 40);
    private JFileChooser fileChooser = new JFileChooser();

    public ChatClient() {
        // Setup GUI
        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.add(textField);
        JButton sendFileButton = new JButton("Send File");
        panel.add(sendFileButton);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);
        frame.pack();

        // Add listeners
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage(textField.getText());
                textField.setText("");
            }
        });

        sendFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendFile();
            }
        });
    }

    private void sendMessage(String message) {
        if (out != null) {
            out.println(EncryptionUtils.encrypt(message));
        }
    }

    private void sendFile() {
        int returnValue = fileChooser.showOpenDialog(frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                MultimediaHandler.sendFile(file, socket);
                messageArea.append("File sent: " + file.getName() + "\n");
            } catch (IOException e) {
                messageArea.append("Error sending file: " + e.getMessage() + "\n");
                e.printStackTrace();
            }
        }
    }

    private void run() throws IOException {
        // Connect to the server
        String serverAddress = JOptionPane.showInputDialog(
                frame,
                "Enter IP Address of the Server:",
                "Welcome to the Chat",
                JOptionPane.QUESTION_MESSAGE);
        socket = new Socket(serverAddress, 8080);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Authentication
        String username = JOptionPane.showInputDialog(
                frame,
                "Enter your username:",
                "Authentication",
                JOptionPane.QUESTION_MESSAGE);
        String password = JOptionPane.showInputDialog(
                frame,
                "Enter your password:",
                "Authentication",
                JOptionPane.QUESTION_MESSAGE);
        out.println(username);
        out.println(password);
        String response = in.readLine();
        if ("Authentication failed!".equals(response)) {
            JOptionPane.showMessageDialog(frame, "Authentication failed!");
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(frame, "Authentication successful!");
            textField.setEditable(true);
        }

        // Process incoming messages
        String message;
        while ((message = in.readLine()) != null) {
            messageArea.append(EncryptionUtils.decrypt(message) + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatClient client = new ChatClient();
            client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            client.frame.setVisible(true);
            try {
                client.run();
            } catch (IOException e) {
                System.err.println("Client error: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
