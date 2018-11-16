package com.testfairy.sniff_her.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.testfairy.sniff_her.R;
import com.testfairy.sniff_her.entity.Message;
import com.testfairy.sniff_her.utility.ObjectUtil;

public class SendMessageView extends FrameLayout {

    public interface OnSendListener {
        void onSend(String message);
    }

    private EditText messageEditText;
    private Button sendButton;
    private OnSendListener sendListener;

    public SendMessageView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public SendMessageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SendMessageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public SendMessageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(@NonNull Context context) {
        inflate(context, R.layout.send_message_view, this);

        messageEditText = findViewById(R.id.message);
        sendButton = findViewById(R.id.send);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                sendButton.setEnabled(s.toString().length() > 0);
            }
        };

        messageEditText.addTextChangedListener(textWatcher);

        sendButton.setEnabled(false);
        sendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sendListener != null) {
                    sendListener.onSend(messageEditText.getText().toString());
                }
            }
        });
    }

    public void setOnSendListener(@Nullable OnSendListener listener) {
        this.sendListener = listener;
    }

    public void setMessage(@NonNull Message message) {
        ObjectUtil.assertNotNull(message);

        messageEditText.setText(message.getMessage());
    }
}
