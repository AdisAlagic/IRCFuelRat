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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.adisalagic.fuelratsirc.IRCTextSender;
import com.adisalagic.fuelratsirc.IRClient;
import com.adisalagic.fuelratsirc.R;
import com.adisalagic.fuelratsirc.ui.Message;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FuelRats extends Fragment {

    private LinearLayout  mChat;
    private ScrollView    mScrollView;
    private IRCTextSender mSender;
    private View          rootView;
    private boolean colored = false;
    FragmentManager manager;

    public static FuelRats newInstance() {
        return new FuelRats();
    }

    ListenerAdapter adapter = new ListenerAdapter() {
        @Override
        public void onGenericMessage(GenericMessageEvent event) throws Exception {
            super.onGenericMessage(event);
            Log.i("IRCConnection", event.getMessage());
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(mChat.getId(), new Message(event.getMessage(), event.getUserHostmask().getNick(), colored, event.getUser().getUserId()));
            transaction.commit();
            colored = !colored;
            mScrollView.fullScroll(View.FOCUS_DOWN);
            mScrollView.fullScroll(View.FOCUS_DOWN);
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
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        IRClient.getInstance().buildConfiguration(adapter);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                IRClient.getInstance().startBot();
                //FIXME AsyncTask Pauses when app collapsed
                ((Activity) rootView.getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(rootView.getContext(), IRClient.getInstance().getBotX().isConnected() + "", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

}