package oodproject;

import java.util.Date;
public class ServerMediator {

    public static void Update(String message) {
        ServerGui.updateGui("--[" + new Date() + "]-- " + message);
    }

    public static void updateUsers(String userlist) {
        ServerGui.updateUsers(userlist);
    }

    public static void clearUsers() {
        ServerGui.clearUsers();
    }
}
 