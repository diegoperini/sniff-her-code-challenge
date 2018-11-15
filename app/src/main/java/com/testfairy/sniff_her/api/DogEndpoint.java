package com.testfairy.sniff_her.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.testfairy.sniff_her.entity.Dog;
import com.testfairy.sniff_her.utility.HttpUtility;
import com.testfairy.sniff_her.utility.ObjectUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class DogEndpoint {

    public Observable<List<Dog>> getDogs(@NonNull final Context context) {
        try {
            ObjectUtil.assertNotNull(context);

            return HttpUtility.httpGetJsonArray(context, Endpoint.baseUrl + "/dog")
                    .map(new Function<JSONArray, List<Dog>>() {
                        @Override
                        public List<Dog> apply(JSONArray jsonArray) throws Exception {
                            List<Dog> list = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                list.add(new Dog(jsonArray.getJSONObject(i)));
                            }

                            return list;
                        }
                    });
        } catch (NullPointerException e) {
            return Observable.error(e);
        }
    }

    public Observable<Dog> markDogAsInterested(@NonNull final Context context, @NonNull final Dog dog) {
        try {
            ObjectUtil.assertNotNull(context, dog);

            return HttpUtility.httpPostJsonObject(context, Endpoint.baseUrl + "/dog/" + dog.getId().toString() + "/interested", null)
                    .map(new Function<JSONObject, Dog>() {
                        @Override
                        public Dog apply(JSONObject jsonObject) throws Exception {
                            return dog;
                        }
                    });
        } catch (NullPointerException e) {
            return Observable.error(e);
        }
    }

    public Observable<Dog> markDogAsNotInterested(@NonNull final Context context, @NonNull final Dog dog) {
        try {
            ObjectUtil.assertNotNull(context, dog);

            return HttpUtility.httpPostJsonObject(context, Endpoint.baseUrl + "/dog/" + dog.getId().toString() + "/not-interested", null)
                    .map(new Function<JSONObject, Dog>() {
                        @Override
                        public Dog apply(JSONObject jsonObject) throws Exception {
                            return dog;
                        }
                    });
        } catch (NullPointerException e) {
            return Observable.error(e);
        }
    }
}
