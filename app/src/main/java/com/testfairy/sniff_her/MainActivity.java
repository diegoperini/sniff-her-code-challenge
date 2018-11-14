package com.testfairy.sniff_her;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.testfairy.sniff_her.api.AuthEndpoint;
import com.testfairy.sniff_her.api.DogEndpoint;
import com.testfairy.sniff_her.api.OwnerEndpoint;
import com.testfairy.sniff_her.entity.Credentials;
import com.testfairy.sniff_her.entity.Dog;
import com.testfairy.sniff_her.utility.HttpUtility;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // This is much shorter to do in Kotlin
    private interface MainActivityState {}
    private class AuthState implements MainActivityState {
        // TODO : add views specific to this state
        public AuthEndpoint endpoint;
        public Credentials credentials;

        // TODO : constructor to set new credentials and inherit views from previous state
    }
    private class FeedState implements MainActivityState {
        // TODO : add views specific to this state
        public DogEndpoint endpoint;
        public List<Dog> dogList;

        // TODO : constructor to set new dogs and inherit views from previous state
    }
    private class SendMessageState implements MainActivityState {
        // TODO : add views specific to this state
        public OwnerEndpoint endpoint;
        public Dog dog;

        // TODO : constructor to set dog and inherit views from previous state
    }

    ///////////////////////////////////////

    private MainActivityState activityState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Prepare request queue
        HttpUtility.getInstance(this);

        // Set initial state
        activityState = new AuthState();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO : activate state
    }

    @Override
    protected void onPause() {
        super.onPause();

        // TODO : deactivate state
    }
}
