package com.adisalagic.fuelratsirc;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class IRCTextSender extends ConstraintLayout {

    private TextView shortComds;
    private TextView caseNum;
    private View rootView;
    private EditText message;
    private ImageView sendButton;
    private Handler handler;

    public IRCTextSender(Context context, AttributeSet attrs) {
        super(context, attrs);
        rootView = inflate(context, R.layout.irc_text_send, this);
        shortComds = findViewById(R.id.short_commands);
        caseNum = findViewById(R.id.case_number);
        message = findViewById(R.id.text);
        sendButton = findViewById(R.id.send_btn);


        shortComds.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(v.getContext());
                //TODO Make custom dialog for selecting commands and make configurable string array in xml
            }
        });

        sendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "";
                if (!caseNum.getText().toString().isEmpty()){
                    msg += "#" + caseNum.getText().toString();
                }
                if (handler != null){
                    handler.onSendButtonClicked(msg + " " + getText());
                }else {
                    Log.i("IRCTextSender", "No handler");
                }
            }
        });
    }

    public String getText(){
        return message.getText().toString();
    }

    public void addHandler(Handler handler){
        this.handler = handler;
    }

    public void removeHandler(){
        handler = null;
    }

    //TODO Make color change for text on selected text

    interface Handler{
        void onSendButtonClicked(String message);
        void onTextTyped(String typedText);
    }
}
