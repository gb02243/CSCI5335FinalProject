package oodproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

public class ClientGui extends JFrame implements Observer {
    private JTextArea chat_display;
    private JTextField message_text;
    private JButton message_send;
    private static ClientObservable clientObservable;

    public ClientGui(ClientObservable clientObservable) {
        this.clientObservable = clientObservable;
        clientObservable.addObserver(this);
        build();
    }

    // build the gui
    private void build() {
        chat_display = new JTextArea(20, 50);
        chat_display.setEditable(false);
        chat_display.setLineWrap(true);
        add(new JScrollPane(chat_display), BorderLayout.CENTER);

        Box box = Box.createHorizontalBox();
        add(box, BorderLayout.SOUTH);
        message_text = new JTextField();
        message_text.setPreferredSize( new Dimension( 200, 100 ) );
        message_send = new JButton("Send");
        box.add(message_text);
        box.add(message_send);

        // action for textfield and button
        ActionListener sendListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message_out = message_text.getText();
                if (message_out != null && message_out.trim().length() > 0)
                    clientObservable.send(message_out);
                message_text.selectAll();
                message_text.requestFocus();
                message_text.setText("");
            }
        };
        message_text.addActionListener(sendListener);
        message_send.addActionListener(sendListener);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                clientObservable.send("/exit");
            }
        });

    }

    // update observers
    public void update(Observable o, Object arg) {
        final Object finalArg = arg;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                chat_display.append(finalArg.toString());
                chat_display.append("\n");
            }
        });
    }

    // return chat_display
    public static ClientObservable getClientObservable() {
        return clientObservable;
    }


    //return clientObservable
    public JTextArea getChat_display() {
        return chat_display;
    }
}
