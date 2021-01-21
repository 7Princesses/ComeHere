package com.example.comehere;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialog extends Dialog {
    private TextView messageText;
    private Button ok_btn;
    private String message;

    private View.OnClickListener okListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        messageText = (TextView)findViewById(R.id.dialogMessageText);
        ok_btn = (Button)findViewById(R.id.okbtn);

        messageText.setText(message);

        if (okListener != null) {
            ok_btn.setOnClickListener(okListener);
        }else {

        }
    }

    public CustomDialog(Context context, String message, View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.message = message;
        this.okListener = singleListener;
    }
}
