package oodproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerGui extends JFrame {
    private static JTextArea chat_display;
    private static JTextArea user_list;
    private static JLabel usertitle;

    public ServerGui() {
        build();
    }

    // build the gui
    private void build() {
        Box hBox = Box.createVerticalBox();
        user_list = new JTextArea(10, 50);
        chat_display = new JTextArea(20, 50);
        chat_display.setEditable(false);
        chat_display.setLineWrap(true);
        hBox.add(new JScrollPane(chat_display), BorderLayout.CENTER);

        usertitle = new JLabel("Connected Users:");
        usertitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        hBox.add(usertitle);
        add(hBox);

        Box box = Box.createHorizontalBox();
        hBox.add(box, BorderLayout.SOUTH);
        box.add(user_list);

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

    static void updateGui(String message) {
        chat_display.append(message);
        chat_display.append("\n");
        chat_display.setCaretPosition(chat_display.getDocument().getLength());
    }

    static void updateUsers(String users) {
        user_list.append(users);
        user_list.append("\n");
    }

    static void clearUsers() {
        user_list.setText("");
    }
}