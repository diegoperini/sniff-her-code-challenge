package com.testfairy.sniff_her.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.testfairy.sniff_her.utility.ObjectUtil;
import com.testfairy.sniff_her.utility.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Owner {
    private @NonNull Integer id;
    private @NonNull String name;
    private @NonNull String favoriteMovie;
    private @NonNull List<String> hobbies = new ArrayList<>();

    public Owner(@NonNull final Integer id, @NonNull final String name, @Nullable final String favoriteMovie, @Nullable final List<String> hobbies) {
        ObjectUtil.assertNotNull(name);
        StringUtil.assertNotEmpty(name);

        this.id = id;
        this.name = name;
        this.favoriteMovie = favoriteMovie == null ? "" : favoriteMovie;
        this.hobbies = hobbies == null ? new ArrayList<String>() : hobbies;
    }

    public Owner(@NonNull final JSONObject json) throws JSONException {
        ObjectUtil.assertNotNull(json);

        id = json.getInt("id");
        name = json.getString("name");
        favoriteMovie = json.optString("favoriteMovie", "");

        JSONArray jsonHobbies = json.getJSONArray("hobbies");
        for (int i = 0; i < jsonHobbies.length(); i++) {
            Object hobby = jsonHobbies.get(i);

            if (hobby instanceof String) hobbies.add((String) hobby);
            else throw new IllegalArgumentException("Constructing an Owner is failed: non String item in hobbies.");
        }

        StringUtil.assertNotEmpty(name);
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getFavoriteMovie() {
        return favoriteMovie;
    }

    @NonNull
    public List<String> getHobies() {
        List<String> copy = new ArrayList<>();
        copy.addAll(hobbies);
        return copy;
    }
}
