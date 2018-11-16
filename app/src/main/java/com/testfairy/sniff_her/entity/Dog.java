package com.testfairy.sniff_her.entity;

import android.support.annotation.NonNull;

import com.testfairy.sniff_her.utility.ObjectUtil;
import com.testfairy.sniff_her.utility.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class Dog {
    public enum Gender {
        MALE, FEMALE;

        @Override
        public String toString() {
            switch (this) {
                case MALE:
                    return "Male";
                case FEMALE:
                    return "Female";
            }

            throw new NullPointerException("Gender cannot be null.");
        }

        @NonNull
        public static Gender fromString(@NonNull final String gender) {
            ObjectUtil.assertNotNull(gender);

            switch (gender) {
                case "Male":
                    return MALE;
                case "Female":
                    return FEMALE;
                default:
                    throw new IllegalArgumentException("Invalid gender found.");
            }
        }
    }

    private @NonNull Integer id;
    private @NonNull Gender gender;
    private @NonNull String picture;
    private @NonNull Owner owner;

    public Dog(@NonNull final Integer id, @NonNull final Gender gender, @NonNull final String picture, @NonNull final Owner owner) {
        ObjectUtil.assertNotNull(gender, picture, owner, id);
        StringUtil.assertNotEmpty(picture);
        StringUtil.assertIsUrl(picture);

        this.id = id;
        this.gender = gender;
        this.picture = picture;
        this.owner = owner;
    }

    public Dog(@NonNull final JSONObject json) throws JSONException {
        ObjectUtil.assertNotNull(json);

        id = json.getInt("id");
        gender = Gender.fromString(json.getString("gender"));
        picture = json.getString("picture");
        owner = new Owner(json.getJSONObject("owner"));
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    @NonNull
    public Gender getGender() {
        return gender;
    }

    @NonNull
    public String getPicture() {
        return picture;
    }

    @NonNull
    public Owner getOwner() {
        return owner;
    }
}
