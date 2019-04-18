package oodproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerGui extends JFrame {
    private JTextArea chat_display;

    public ServerGui() {
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

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Server.getServer().close();
                } catch (Exception ex) {
                    chat_display.append(ex.toString());
                    chat_display.append("\n");
                }
            }
        });
    }
}
