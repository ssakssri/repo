package com.ssakssri.scp.myhrext.util;

import java.net.InetAddress;
import java.net.PasswordAuthentication;

public class Authenticator extends java.net.Authenticator {

    protected PasswordAuthentication getPasswordAuthentication() {
        String promptString = getRequestingPrompt();
        System.out.println(promptString);
        String hostname = getRequestingHost();
        System.out.println(hostname);
        InetAddress ipaddr = getRequestingSite();
        System.out.println(ipaddr);
        int port = getRequestingPort();

        String username = "ICBC";
        String password = "y1269Min.";
        return new PasswordAuthentication(username, password.toCharArray());
    }

}
