package com.testfairy.sniff_her.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.testfairy.sniff_her.entity.AuthenticationToken;
import com.testfairy.sniff_her.entity.Credentials;
import com.testfairy.sniff_her.utility.HttpUtility;
import com.testfairy.sniff_her.utility.ObjectUtil;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class AuthEndpoint {

    public Observable<AuthenticationToken> authenticate(@NonNull final Context context, @NonNull final Credentials credentials) {
        try {
            ObjectUtil.assertNotNull(context, credentials);

            return HttpUtility.httpPostJsonObject(context, Endpoint.baseUrl + "/auth", credentials.toJsonObject())
                    .map(new Function<JSONObject, AuthenticationToken>() {
                        @Override
                        public AuthenticationToken apply(JSONObject jsonObject) throws Exception {
                            return new AuthenticationToken(jsonObject);
                        }
                    });
        } catch (JSONException | NullPointerException e) {
            return Observable.error(e);
        }
    }

}
