package com.adisalagic.fuelratsirc.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
//        authorColor = Color.rgb(r, g, b);
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_message, null);
        mFullMessage = rootView.findViewById(R.id.full_message);
        Spanned coloredAuthor = Html.fromHtml("<font color='" + authorColor + "'>" + author + ": </font>" + message);
        mFullMessage.setText(coloredAuthor);
        if (colored) {
            mFullMessage.setBackgroundColor(Color.DKGRAY);
        }

        return rootView;
    }

    private String colorizeMessage() {
        return null;
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
