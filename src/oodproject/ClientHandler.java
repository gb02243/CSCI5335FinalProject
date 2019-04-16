package oodproject;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.Socket;

class ClientHandler extends Thread {
    private String username = null;
    private DataInputStream din = null;
    private PrintStream dout = null;
    private Socket s = null;
    private final ClientHandler[] threads;
    private final int clientCount;
    private final ChatStatus joinMessage = new JoinChat();
    private final ChatStatus leaveMessage = new LeaveChat();

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

            UserList.userNames.add(name);

            synchronized (this) {
                for (int i = 0; i < clientCount; i++) {
                    username = name;
                    break;
                }
                for (int i = 0; i < clientCount; i++) {
                    if (threads[i] != null && threads[i] != this) {
                    	threads[i].dout.println(joinMessage.identify(name));
                    }
                }
            }

            // send messages
            while (true) {
                String message = din.readLine();
                if (message.startsWith("/exit")) {
                    break;
                }
                // iterator
                if (message.startsWith("/list")) {
                    dout.println("\nConnected Users: ");
                    for (java.util.Iterator<String> iter = UserList.userNames.iterator(); iter.hasNext(); ) {
                        String userNameIter = (String) iter.next();
                        dout.println("  - " + userNameIter);
                    }
                    dout.println();
                } else {
                    synchronized (this) {
                        for (int i = 0; i < clientCount; i++) {
                            if (threads[i] != null && threads[i].username != null) {
                                threads[i].dout.println("<" + name + ">: " + message);
                            }
                        }
                    }
                }
            }

            // handle user leaving
            synchronized (this) {
                for (int i = 0; i < clientCount; i++) {
                    if (threads[i] != null && threads[i] != this
                            && threads[i].username != null) {
                        threads[i].dout.println(leaveMessage.identify(name));
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

            // remove from UserList
            synchronized (this) {
                for (int j = 0; j < UserList.userNames.size(); j++) {
                    if (UserList.userNames.get(j) == name) {
                        UserList.userNames.remove(j);
                    }
                }
            }

            // close streams and socket
            din.close();
            dout.close();
            s.close();
        }
        catch (NullPointerException e){
            // broadcast leave message
            synchronized (this) {
                for (int i = 0; i < clientCount; i++) {
                    if (threads[i] != null && threads[i] != this
                            && threads[i].username != null) {
                        threads[i].dout.println(leaveMessage.identify(username));
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

            // remove from UserList
            synchronized (this) {
                for (int j = 0; j < UserList.userNames.size(); j++) {
                    if (UserList.userNames.get(j) == username) {
                        UserList.userNames.remove(j);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}