package com.adisalagic.fuelratsirc.ui.fuelrats;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.adisalagic.fuelratsirc.IRCTextSender;
import com.adisalagic.fuelratsirc.IRClient;
import com.adisalagic.fuelratsirc.R;
import com.adisalagic.fuelratsirc.ui.Message;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectAttemptFailedEvent;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.DisconnectEvent;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.UUID;

public class FuelRats extends Fragment {

    private LinearLayout  mChat;
    private ScrollView    mScrollView;
    private IRCTextSender mSender;
    private View          rootView;
    private boolean collectMode = false;
    FragmentManager manager;

    public static FuelRats newInstance() {
        return new FuelRats();
    }

    ListenerAdapter adapter = new ListenerAdapter() {
        @Override
        public void onGenericMessage(GenericMessageEvent event) throws Exception {
            super.onGenericMessage(event);
            Log.i("IRCConnection", event.getMessage());
            if (collectMode){
                IRClient.getInstance().events.add(event);
            }
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(mChat.getId(), new Message(event.getMessage(), event.getUserHostmask().getNick(), event.getUser().getUserId()));
            transaction.commit();
            ((Activity) rootView.getContext()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mScrollView.fullScroll(View.FOCUS_DOWN);
                    mScrollView.fullScroll(View.FOCUS_DOWN);
                }
            });
        }

        @Override
        public void onDisconnect(DisconnectEvent event) throws Exception {
            super.onDisconnect(event);
            addSystemMessage("Disconnected!");
        }

        @Override
        public void onJoin(JoinEvent event) throws Exception {
            super.onJoin(event);
            if (!event.getUserHostmask().getNick().equals(IRClient.getInstance().getBotX().getNick())){
                addSystemMessage("User " + event.getUserHostmask().getNick() + "has connected");
            }
        }

        @Override
        public void onConnect(ConnectEvent event) throws Exception {
            super.onConnect(event);
            addSystemMessage("Connection success!");
            ((Activity) rootView.getContext()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mSender.setEnabled(true);
                }
            });
        }


        @Override
        public void onConnectAttemptFailed(ConnectAttemptFailedEvent event) throws Exception {
            super.onConnectAttemptFailed(event);
            addSystemMessage("Connection failed, attempts left: " + event.getRemainingAttempts());
        }


    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fuel_rats_fragment, container, false);
        mChat = rootView.findViewById(R.id.chat);
        mScrollView = rootView.findViewById(R.id.scroller);
        mSender = rootView.findViewById(R.id.sender);
        manager = getChildFragmentManager();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        mSender.setEnabled(false);
        mSender.addHandler(new IRCTextSender.Handler() {
            @Override
            public void onSendButtonClicked(View v, final String message) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        IRClient.getInstance().getBotX().sendIRC().message(IRClient.RAT_CHAT, message);
                        ((Activity) rootView.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mScrollView.fullScroll(View.FOCUS_DOWN);
                                FragmentTransaction transaction = manager.beginTransaction();
                                transaction.add(mChat.getId(), new Message(message, IRClient.getInstance().getLogin(),
                                        IRClient.getInstance().getBotX().getUserBot().getUserId()));
                                transaction.commit();
                                mScrollView.fullScroll(View.FOCUS_DOWN);
                            }
                        });
                    }
                });

            }

            @Override
            public void onTextTyped(String typedText) {

            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        IRClient.getInstance().buildConfiguration(adapter);
        addSystemMessage("Connecting...");
        Intent intent = new Intent("START_SERVICE");
        LocalBroadcastManager.getInstance(rootView.getContext()).sendBroadcast(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        collectMode = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        collectMode = false;
        if (IRClient.getInstance().events.size() == 0){
            return;
        }
        FragmentTransaction transaction = manager.beginTransaction();
        for (GenericMessageEvent event : IRClient.getInstance().events){
            Message msg = new Message(event.getMessage(), event.getUserHostmask().getNick(),
                    event.getUser().getUserId());
            transaction.add(mChat.getId(), msg);
        }
        transaction.commit();
        IRClient.getInstance().events.clear();
    }

    private void addSystemMessage(String message){
        FragmentTransaction transaction = manager.beginTransaction();
        UUID uuid = UUID.fromString("8d28ed28-9d1b-4e49-adb8-9555911d59a6"); //Let System will have one color
        Message msg = new Message(message, "System", uuid);
        transaction.add(mChat.getId(), msg);
        transaction.commit();
    }
}