package oodproject;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private String username = null;
    private DataInputStream din = null;
    private PrintStream dout = null;
    private Socket s = null;
    private final ClientHandler[] threads;
    private int clientCount;
    private ChatStatus joinMessage = new JoinChat();

    public ClientHandler(Socket s, ClientHandler[] threads) {
        this.s = s;
        this.threads = threads;
        clientCount = threads.length;
    }

    public void run() {
        int clientCount = this.clientCount;
        ClientHandler[] threads = this.threads;

        try {
            din = new DataInputStream(s.getInputStream());
            dout = new PrintStream(s.getOutputStream());
            String name;
            while (true) {
                name = din.readLine().trim();
                break;
            }

            dout.println("Connected as: " + name
                    + " type /exit to leave");
            synchronized (this) {
                for (int i = 0; i < clientCount; i++) {
                    username = "@" + name;
                    break;
                }
                for (int i = 0; i < clientCount; i++) {
                    if (threads[i] != null && threads[i] != this) {
                        threads[i].dout.println("--- "+name+" has joined the chat ---");
                    }
                }
            }

            // send messages
            while (true) {
                String message = din.readLine();
                if (message.startsWith("/exit")) {
                    break;
                }
                synchronized (this) {
                    for (int i = 0; i < clientCount; i++) {
                        if (threads[i] != null && threads[i].username != null) {
                            threads[i].dout.println("<" + name + ">: " + message);
                        }
                    }
                }

            }

            // handle user leaving
            synchronized (this) {
                for (int i = 0; i < clientCount; i++) {
                    if (threads[i] != null && threads[i] != this
                            && threads[i].username != null) {
                        threads[i].dout.println(joinMessage.identify(name));
                    }
                }
            }

            // allow new connections
            synchronized (this) {
                for (int i = 0; i < clientCount; i++) {
                    if (threads[i] == this) {
                        threads[i] = null;
                    }
                }
            }

            // close streams and socket
            din.close();
            dout.close();
            s.close();
        } catch (IOException e) {
        }
    }

}
