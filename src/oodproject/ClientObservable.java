package oodproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;

class ClientObservable extends Observable {
    private Socket s;
    private OutputStream dout;

    @Override
    public void notifyObservers(Object arg) {
        super.setChanged();
        super.notifyObservers(arg);
    }

    // create socket for observers
    void InitSocket(String server, int port) throws IOException {
        s = new Socket(server, port);
        dout = s.getOutputStream();
        Thread receivingThread = new Thread() {
            @Override
            public void run() {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null)
                        notifyObservers(line);
                } catch (IOException ex) {
                    notifyObservers(ex);
                }
            }
        };
        receivingThread.start();
    }

    // carriage return
    private static final String CRLF = "\r\n";

    // send message
    void send(String text) {
        try {
            dout.write((text + CRLF).getBytes());
            dout.flush();
        } catch (IOException ex) {
            notifyObservers(ex);
        }
    }

    // close socket
    void close() {
        try {
            s.close();
        } catch (IOException ex) {
            notifyObservers(ex);
        }
    }
}
