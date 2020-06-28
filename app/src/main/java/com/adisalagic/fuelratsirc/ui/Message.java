package com.adisalagic.fuelratsirc.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.adisalagic.fuelratsirc.R;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.UUID;

public class Message extends Fragment {
    String  message;
    String  author;
    View    rootView;
    String  authorColor;
    boolean colored;
    UUID uuid;
    private TextView mFullMessage;

    public Message(String message, String author, boolean colored, UUID uuid) {
        this.message = message;
        this.author = author;
        this.colored = colored;
        this.uuid = uuid;
        Random random = new Random(UuidToLong());
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        authorColor = String.format("#%02x%02x%02x", r, g, b);
        this.message = Html.escapeHtml(message);
//        authorColor = Color.rgb(r, g, b);
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_message, null);
        mFullMessage = rootView.findViewById(R.id.full_message);
        Spanned coloredAuthor = colorizeMessage();
        mFullMessage.setText(coloredAuthor);
        if (colored) {
            mFullMessage.setBackgroundColor(Color.DKGRAY);
        }
        rootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) ((Activity) v.getContext()).getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("message", message);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(v.getContext(), "Copped to clipboard", Toast.LENGTH_SHORT).show();
                return true;
            }
        });


        return rootView;
    }

    private Spanned colorizeMessage() {
        Spanned coloredAuthor;
        StringBuilder newMessage = new StringBuilder();
        String[] words = message.split(" ");
        for (int i = 0; i < words.length; i++){
            if (Patterns.WEB_URL.matcher(words[i]).matches()){
                words[i] = "<a href='" + words[i] + "'>" + words[i] + "</a>";
            }
            newMessage.append(words[i]).append(" ");
        }
        this.message = newMessage.toString();
        if (Patterns.WEB_URL.matcher(message).matches()){
            coloredAuthor = Html.fromHtml("<font color='" + authorColor + "'>" + author + ": </font>" +
                    "<a href='" + message + "'>" + message + "</a>");
        }else {
            coloredAuthor = Html.fromHtml("<font color='" + authorColor + "'>" + author + ": </font>" + message);
        }
        return coloredAuthor;
    }

    private long UuidToLong(){
        long val;
        final ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(uuid.getLeastSignificantBits());
        buffer.putLong(uuid.getMostSignificantBits());
        final BigInteger bi = new BigInteger(buffer.array());
        val = bi.longValue();
        return val;
    }

}
