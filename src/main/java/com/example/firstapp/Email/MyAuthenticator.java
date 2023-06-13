package com.example.firstapp.Email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthenticator extends Authenticator {
    public String login;
    public String password;

    public MyAuthenticator(String login, String password)
    {
        this.login = login;
        this.password = password;

        getPasswordAuthentication();
    }

    protected PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(login, password);
    }
}
