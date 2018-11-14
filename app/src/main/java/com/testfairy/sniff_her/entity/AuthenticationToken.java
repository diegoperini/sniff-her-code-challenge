package com.testfairy.sniff_her.entity;

import android.support.annotation.NonNull;

import com.testfairy.sniff_her.utility.ObjectUtil;
import com.testfairy.sniff_her.utility.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthenticationToken {
    private @NonNull String token;

    public AuthenticationToken(@NonNull final String token) {
        ObjectUtil.assertNotNull(token);
        StringUtil.assertNotEmpty(token);

        this.token = token;
    }

    public AuthenticationToken(@NonNull final JSONObject json) throws JSONException {
        this.token = json.getString("token");

        ObjectUtil.assertNotNull(token);
        StringUtil.assertNotEmpty(token);
    }

    @NonNull
    public String getToken() {
        return token;
    }
}
