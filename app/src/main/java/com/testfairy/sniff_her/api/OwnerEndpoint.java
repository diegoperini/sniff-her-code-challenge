package com.testfairy.sniff_her.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.testfairy.sniff_her.entity.Message;
import com.testfairy.sniff_her.entity.Owner;
import com.testfairy.sniff_her.utility.HttpUtility;
import com.testfairy.sniff_her.utility.ObjectUtil;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class OwnerEndpoint {

    public Observable<Owner> sendMessage(@NonNull final Context context, @NonNull final Owner owner, @NonNull final Message message) {
        try {
            ObjectUtil.assertNotNull(context, owner, message);

            return HttpUtility.httpPostJsonObject(context, Endpoint.baseUrl + "/owner/" + owner.getId().toString() + "/message", message.toJsonObject())
                    .map(new Function<JSONObject, Owner>() {
                        @Override
                        public Owner apply(JSONObject jsonObject) throws Exception {
                            return owner;
                        }
                    });
        } catch (NullPointerException | JSONException e) {
            return Observable.error(e);
        }
    }

}
