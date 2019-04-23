package oodproject;

import javax.swing.*;

class Client {
    public static void main(String[] args) {
        int port = 1201;
        ClientObservable observable = new ClientObservable();

        ClientGuiFacade frame = new ClientGuiFacade(observable);
        frame.setWindow("Client");

        boolean connected = false;

        while (!connected) {
            // get IP from dialog
            JFrame addressdialog = new JFrame("Server Information");
            String server_address = JOptionPane.showInputDialog(addressdialog, "Enter the Server IP:", "127.0.0.1");

            // connect to server
            try {
                observable.InitSocket(server_address, port);
                connected = true;
                frame.setTitle("Client - Connected");
            } catch (Exception e) {
                System.out.println("Cannot connect to " + server_address + ":" + port);
                ((ClientGui) frame).getChat_display().append("Cannot connect to " + server_address + ":" + port + "\n");
                connected = false;
            }
        }

        // get username from dialog
        JFrame userdialog = new JFrame("User Information");
        String uname = JOptionPane.showInputDialog(userdialog, "Enter your username:");
        ClientGui.getClientObservable().send(uname);
        frame.setTitle("Client - Connected - " + uname);
    }
}
