package com.testfairy.sniff_her.entity;

import android.support.annotation.NonNull;

import com.testfairy.sniff_her.utility.ObjectUtil;
import com.testfairy.sniff_her.utility.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class Credentials {
    private @NonNull String username;
    private @NonNull String password;

    public Credentials(@NonNull final String username, @NonNull final String password) {
        ObjectUtil.assertNotNull(username, password);
        StringUtil.assertNotEmpty(username, password);

        this.username = username;
        this.password = password;
    }

    public JSONObject toJsonObject() throws JSONException {
        JSONObject json = new JSONObject();

        json.put("username", username);
        json.put("password", password);

        return json;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }
}
