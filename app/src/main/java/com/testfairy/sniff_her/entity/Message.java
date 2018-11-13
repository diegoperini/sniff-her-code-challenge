package com.testfairy.sniff_her.entity;

import android.support.annotation.NonNull;

import com.testfairy.sniff_her.utility.ObjectUtil;
import com.testfairy.sniff_her.utility.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
    private @NonNull Integer recipientOwnerId;
    private @NonNull String message;

    public Message(@NonNull Integer recipientOwnerId, @NonNull String message) {
        ObjectUtil.assertNotNull(recipientOwnerId, message);
        StringUtil.assertNotEmpty(message);

        this.recipientOwnerId = recipientOwnerId;
        this.message = message;
    }

    public JSONObject toJsonObject() throws JSONException {
        JSONObject json = new JSONObject();

        json.put("recipientOwnerId", recipientOwnerId);
        json.put("message", message);

        return json;
    }

    @NonNull
    public Integer getRecipientOwnerId() {
        return recipientOwnerId;
    }

    @NonNull
    public String getMessage() {
        return message;
    }
}
