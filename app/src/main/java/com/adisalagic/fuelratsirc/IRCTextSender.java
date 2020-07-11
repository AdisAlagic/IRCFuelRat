package com.adisalagic.fuelratsirc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.common.collect.HashMultimap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class IRCTextSender extends ConstraintLayout {

    private TextView  shortComds;
    private TextView  caseNum;
    private View      rootView;
    private EditText  message;
    private ImageView sendButton;
    private Handler   handler;
    private String[]  checkBoxes = {
            "fr+",
            "fr-",
            "wr+",
            "wr-",
            "bc+",
            "bc-",
            "fuel+",
            "fuel-",
            "prep-",
            "inst-",
            "in EZ",
            "in OP",
            "in PG",
            "in Solo",
            "in MM"
    };

    String typedMessage = "";

    public IRCTextSender(Context context, AttributeSet attrs) {
        super(context, attrs);
        rootView = inflate(context, R.layout.irc_text_send, this);
        shortComds = findViewById(R.id.short_commands);
        caseNum = findViewById(R.id.case_number);
        message = findViewById(R.id.text);
        sendButton = findViewById(R.id.send_btn);
        handler = new Handler() {
            @Override
            public void onSendButtonClicked(View v, String msg) {
                Toast.makeText(v.getContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public ArrayList<String> onCollectPeopleOnline() {
                return new ArrayList<>();
            }

        };
        caseNum.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                View           view = inflate(v.getContext(), R.layout.dialog_case_number, null);
                final EditText text = view.findViewById(R.id.editTextNumberSigned);
                Button         ok   = view.findViewById(R.id.submit);

                final AlertDialog dialog = new AlertDialog.Builder(v.getContext()).setTitle("Номер дела").setView(view).create();
                dialog.show();
                ok.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (text.getText().toString().isEmpty()) {
                            caseNum.setText("#");
                        } else {
                            caseNum.setText(text.getText());
                        }
                        dialog.dismiss();
                    }
                });
            }
        });

        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                typedMessage = s.toString();
                PopupWindow popupWindow = new PopupWindow(rootView.getContext());
                popupWindow.setWidth(message.getWidth());
                LinearLayout layout = new LinearLayout(rootView.getContext());
                ArrayList<String> online = handler.onCollectPeopleOnline();
                ScrollView scrollView = new ScrollView(rootView.getContext());
                String[] words = s.toString().split(" ");
                for (final String word : words){
                    if (word.matches("@[a-zA-Z!]+")){
                        ArrayList<String> close = getTopNSimularWords(10, word.replace('@', ' '), (String[]) online.toArray());
                        for (String sim : close){
                            final TextView textView = new TextView(rootView.getContext());
                            textView.setText(sim);
                            textView.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    message.setText(typedMessage.replaceAll("@[a-zA-Z!]+", textView.getText().toString()));
                                }
                            });
                            layout.addView(textView);
                        }
                    }
                }
                scrollView.addView(layout);
                popupWindow.setContentView(scrollView);
                popupWindow.showAtLocation(message, Gravity.CENTER, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            }
        });

        shortComds.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity          activity = (Activity) v.getContext();
                final PopupWindow window   = new PopupWindow(v.getContext());
                window.setFocusable(true);
                View                      windowView  = inflate(v.getContext(), R.layout.popup_checkboxes, null);
                LinearLayout              layout      = windowView.findViewById(R.id.lay);
                final ArrayList<CheckBox> mCheckBoxes = new ArrayList<>();
                for (String str : checkBoxes) {
                    CheckBox checkBox = new CheckBox(new ContextThemeWrapper(windowView.getContext(), androidx.appcompat.R.style.Theme_AppCompat_DayNight));
                    checkBox.setTextColor(Color.WHITE);
                    checkBox.setText(str);
                    mCheckBoxes.add(checkBox);
                    layout.addView(checkBox);
                }
                Button add = new Button(windowView.getContext());
                add.setText("Добавить");
                add.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StringBuilder mesg = new StringBuilder();
                        for (CheckBox checkBox : mCheckBoxes) {
                            if (checkBox.isChecked()) {
                                mesg.append(checkBox.getText())
                                        .append(" ");
                            }
                        }
                        if (!typedMessage.isEmpty()) {
                            mesg.append(typedMessage);
                        }
                        message.setText(mesg);
                        window.dismiss();
                    }
                });
                layout.addView(add);
                window.setContentView(windowView);

                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null && activity.getCurrentFocus() != null) {
                    imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                }
                window.showAtLocation(shortComds, Gravity.START, LinearLayout.LayoutParams.MATCH_PARENT, -1);
            }
        });

        sendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "";
                if (!caseNum.getText().toString().isEmpty()) {
                    if (!caseNum.getText().toString().equals("#")) {
                        msg += "#" + caseNum.getText().toString();
                    }
                }
                if (handler != null) {
                    handler.onSendButtonClicked(v, msg + " " + getText());
                    typedMessage = "";
                    message.setText("");
                } else {
                    Log.i("IRCTextSender", "No handler");
                }
            }
        });
    }

    public String getText() {
        return message.getText().toString();
    }

    public void addHandler(Handler handler) {
        this.handler = handler;
    }

    public void removeHandler() {
        handler = null;
    }

    //TODO Make color change for text on selected text

    public interface Handler {
        void onSendButtonClicked(View v, String message);
        ArrayList<String> onCollectPeopleOnline();
    }

    public void setEnabled(boolean enabled) {
        shortComds.setEnabled(enabled);
        caseNum.setEnabled(enabled);
        sendButton.setEnabled(enabled);
        message.setEnabled(enabled);

    }

    private static int compareWithSimularSymbols(String a, String b){
        a = a.toLowerCase();
        b = b.toLowerCase();
        char[] aArray = a.toCharArray();
        char[] bArray = b.toCharArray();
        int ok = 0;
        for (char aCh : aArray){
            for (char bCh : bArray){
                if (aCh == bCh){
                    ok++;
                }
            }
        }
        return ok;
    }

    //I need better algorithm
    private static ArrayList<String> getTopNSimularWords(int N, String source, String[] words){
        TreeMap<Integer, String> pairs = new TreeMap<>();
        for (String word : words){
            pairs.put(compareWithSimularSymbols(source, word), word);
        }
        ArrayList<String>             strings = new ArrayList<>();
        NavigableMap<Integer, String> reverse = pairs.descendingMap();
        int                           i       = 0;
        for (Map.Entry<Integer, String> entry : reverse.entrySet()){
            if (i == N){
                break;
            }
            strings.add(entry.getValue());
            i++;
        }
        return strings;
    }

}
