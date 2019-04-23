package oodproject;

import javax.swing.*;

public class ClientGuiFacade extends ClientGui {
    public ClientGuiFacade(ClientObservable clientObservable) {
        super(clientObservable);

    }

    public void setWindow(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
