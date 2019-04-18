package oodproject;

import java.util.Date;
public class ServerMediator {

    public static void Update(String message) {
        ServerGui.updateGui("--[" + new Date() + "]--\t " + message);
    }
}
 