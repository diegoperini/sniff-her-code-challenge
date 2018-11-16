package com.testfairy.sniff_her.entity;

import android.support.annotation.NonNull;

import com.testfairy.sniff_her.utility.ObjectUtil;
import com.testfairy.sniff_her.utility.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
    private @NonNull String message;

    public Message(@NonNull final String message) {
        ObjectUtil.assertNotNull(message);

        this.message = message;
    }

    public JSONObject toJsonObject() throws JSONException {
        JSONObject json = new JSONObject();

        json.put("message", message);

        return json;
    }

    @NonNull
    public String getMessage() {
        return message;
    }
}
