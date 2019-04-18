package oodproject;

import javax.swing.*;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

class Server {
    private static ServerSocket ss = null;
    private static Socket s = null;

    private static final int maxClients = 10;
    private static final ClientHandler[] threads = new ClientHandler[maxClients];

    public static void main(String[] args) {

        // display gui
        JFrame frame = new ServerGui();
        frame.setTitle("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        try {
            ss = new ServerSocket(1201);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //create a new socket & thread for each client
        while (true) {
            try {
                s = ss.accept();
                ServerMediator.Update("User is attempting to connect");
                int i = 0;
                for (i = 0; i < maxClients; i++) {
                    if (threads[i] == null) {
                        (threads[i] = new ClientHandler(s, threads)).start();
                        break;
                    }
                }
                if (i == maxClients) {
                    PrintStream os = new PrintStream(s.getOutputStream());
                    os.println(maxClients + " clients are already connected.");
                    os.close();
                    s.close();
                }
            } catch (Exception e) {
                // TODO disconnect clients
                e.printStackTrace();
                System.exit(0);
            }
        } 
    }

    public static ServerSocket getServer() {
        return ss;
    }

}