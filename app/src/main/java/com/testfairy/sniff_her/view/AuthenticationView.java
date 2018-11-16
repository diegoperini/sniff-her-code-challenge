package com.testfairy.sniff_her.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.testfairy.sniff_her.R;
import com.testfairy.sniff_her.utility.ObjectUtil;

public class AuthenticationView extends FrameLayout {

    public interface OnSignInListener {
        void onSignIn(String username, String password);
    }

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private OnSignInListener signInListener;

    public AuthenticationView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public AuthenticationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AuthenticationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public AuthenticationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(@NonNull Context context) {
        inflate(context, R.layout.authentication_view, this);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        signInButton = findViewById(R.id.sign_in);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                signInButton.setEnabled(s.toString().length() > 0 && passwordEditText.getText().toString().length() > 0);
            }
        };

        usernameEditText.addTextChangedListener(textWatcher);
        passwordEditText.addTextChangedListener(textWatcher);

        signInButton.setEnabled(false);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signInListener != null) {
                    signInListener.onSignIn(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                }
            }
        });
    }

    public void setCredentials(@NonNull String username, @NonNull String password) {
        ObjectUtil.assertNotNull(username, password);

        usernameEditText.setText(username);
        passwordEditText.setText(password);
    }

    public void setOnSignInListener(@Nullable OnSignInListener listener) {
        this.signInListener = listener;
    }

}
