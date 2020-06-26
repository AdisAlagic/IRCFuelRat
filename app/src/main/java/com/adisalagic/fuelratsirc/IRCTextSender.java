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
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

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
            public void onTextTyped(String typedText) {
                typedMessage = typedText;
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
                handler.onTextTyped(s.toString());
            }
        });

        shortComds.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity           activity = (Activity) v.getContext();
                final PopupWindow window = new PopupWindow(v.getContext());
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
                        if (!typedMessage.isEmpty()){
                            mesg.append(typedMessage);
                        }
                        message.setText(mesg);
                        window.dismiss();
                    }
                });
                layout.addView(add);
                window.setContentView(windowView);

                InputMethodManager imm      = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
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
                    if (!caseNum.getText().toString().equals("#")){
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

    interface Handler {
        void onSendButtonClicked(View v, String message);

        void onTextTyped(String typedText);
    }
}
