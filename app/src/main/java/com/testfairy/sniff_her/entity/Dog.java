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
                    return "male";
                case FEMALE:
                    return "female";
            }

            throw new NullPointerException("Gender cannot be null.");
        }

        public static Gender fromString(@NonNull String gender) {
            switch (gender) {
                case "male":
                    return MALE;
                case "female":
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

    public Dog(@NonNull Integer id, @NonNull Gender gender, @NonNull String picture, @NonNull Owner owner) {
        ObjectUtil.assertNotNull(gender, picture, owner, id);
        StringUtil.assertNotEmpty(picture);
        StringUtil.assertIsUrl(picture);

        this.id = id;
        this.gender = gender;
        this.picture = picture;
        this.owner = owner;
    }

    public Dog(@NonNull JSONObject json) throws JSONException {
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
