package com.adisalagic.fuelratsirc;

import android.util.Log;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.managers.ListenerManager;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.IOException;
import java.util.ArrayList;

public class IRClient {

    private static volatile IRClient mInstance;
    public static           String   RAT_CHAT = "#ratchat";

    private IRCAdapter    adapter;
    private PircBotX      botX;
    private Configuration configuration;
    private String        login;
    private String        password;

    public ArrayList<GenericMessageEvent> events = new ArrayList<>();

    private IRClient() {
        adapter = new IRCAdapter();
    }

    public static IRClient getInstance() {
        if (mInstance == null) {
            synchronized (IRClient.class) {
                if (mInstance == null) {
                    mInstance = new IRClient();
                }
            }
        }
        return mInstance;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoginAndPassword(String login, String password) {
        setLogin(login);
        setPassword(password);
    }

    public void buildConfiguration() {
        Configuration.Builder builder = new Configuration.Builder();
        builder
                .addServer("irc.fuelrats.com")
                .addAutoJoinChannel(RAT_CHAT)
                .addListener(adapter)
                .setName(login)
                .setAutoReconnect(true)
                .setRealName(login)
                .setFinger("Client by AdisAlagic")
                .setLogin(login);
        if (!password.isEmpty()) {
            builder.setNickservNick(password);
        }
        configuration = builder.buildConfiguration();
    }

    public String getLogin() {
        return login;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setBotX(PircBotX botX) {
        this.botX = botX;
    }

    public void startBot() {
        if (botX == null) {
            botX = new PircBotX(configuration);
        }
        try {
            botX.startBot();
        } catch (IOException | IrcException e) {
            Log.e("IRC", "No connection" + e);
            e.printStackTrace();
        }

    }

    public PircBotX getBotX() {
        return botX;
    }

    public IRCAdapter getAdapter() {
        return adapter;
    }
}
