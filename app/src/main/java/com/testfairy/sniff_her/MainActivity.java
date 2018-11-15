package com.testfairy.sniff_her;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;

import com.testfairy.sniff_her.api.AuthEndpoint;
import com.testfairy.sniff_her.api.DogEndpoint;
import com.testfairy.sniff_her.api.OwnerEndpoint;
import com.testfairy.sniff_her.entity.AuthenticationToken;
import com.testfairy.sniff_her.entity.Credentials;
import com.testfairy.sniff_her.entity.Dog;
import com.testfairy.sniff_her.entity.Message;
import com.testfairy.sniff_her.entity.Owner;
import com.testfairy.sniff_her.utility.HttpUtility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

////////////////////// READ THIS ////////////////////////////////////////////////////////////////////////
//
// This Activity is a simple state machine.
//
// Each state has its own views and data.
//
// Each state is responsible for finding and caching its views.
//
// Each state is responsible for defining work about its data,
// but they never run the job themselves. Instead, defined works
// are run in Activity scope for unified orchestration.
//
// Activity decides when to change states at the end of a executed job.
// Observable classes of Rx library is used for asynchronous job orchestration.
// In other words, a job is an Observable subscription.
//
// Look for methods that looks like "handleAuthState", "handleFeedState" etc
// to understand what each state is responsible for.
//
//////////////////////////////////////////////////////////////////////////////////////////////////////////
public class MainActivity extends AppCompatActivity {

    // Interface shared by all states of the activity
    private interface MainActivityState {
        boolean haveMissingViews();
        void findAndStoreViews(ViewGroup root, Map<MainActivityState, MainActivityViews> viewCache);
    }

    // Interface shared by all views holders of the activity states
    private interface MainActivityViews {}

    // Authentication activity state
    private static class AuthState implements MainActivityState {
        // TODO : add views specific to this state
        private class Views implements MainActivityViews {}

        public AuthEndpoint endpoint;
        public Credentials credentials;

        // TODO : constructor to set new credentials and inherit views from previous state

        @Override
        public boolean haveMissingViews() {
            // TODO : check if stored views are null view ObjectUtil.allNotNull()
            return false;
        }

        @Override
        public void findAndStoreViews(ViewGroup root, Map<MainActivityState, MainActivityViews> viewCache) {
            // TODO : implement after xml layout is ready
        }
    }

    // Dog feed activity state
    private static class FeedState implements MainActivityState {
        // TODO : add views specific to this state
        private class Views implements MainActivityViews {}

        public DogEndpoint endpoint;
        public List<Dog> dogList;

        // TODO : constructor to set new dogs and inherit views from previous state

        @Override
        public boolean haveMissingViews() {
            // TODO : check if stored views are null view ObjectUtil.allNotNull()
            return false;
        }

        @Override
        public void findAndStoreViews(ViewGroup root, Map<MainActivityState, MainActivityViews> viewCache) {
            // TODO : implement after xml layout is ready
        }
    }

    // Send message to a dog owner activity state
    private static class SendMessageState implements MainActivityState {
        // TODO : add views specific to this state
        private class Views implements MainActivityViews {}

        public OwnerEndpoint endpoint;
        public Dog dog;

        // TODO : constructor to set dog and inherit views from previous state

        @Override
        public boolean haveMissingViews() {
            // TODO : check if stored views are null view ObjectUtil.allNotNull()
            return false;
        }

        @Override
        public void findAndStoreViews(ViewGroup root, Map<MainActivityState, MainActivityViews> viewCache) {
            // TODO : implement after xml layout is ready
        }
    }

    ///////////////////////////////////////

    // Activity Properties

    private MainActivityState activityState = new AuthState();; // Current activity state
    private Map<MainActivityState, MainActivityViews> viewCache = new HashMap<>(); // A cache for storing found views

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Prepare request queue
        HttpUtility.getInstance(this);

        // Set initial state as Auth
        activityState = new AuthState();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Prepare state again just in case they lost persistence for views, resources etc
        prepareStates();
    }

    // State Orchestration

    private void prepareStates() {
        // Check current state if it knows its own views. This should
        // only happen once per state type, 3 times per app lifetime
        // in this case.
        if (activityState.haveMissingViews()) {
            activityState.findAndStoreViews((ViewGroup) findViewById(R.id.rootView), viewCache);
        }

        // Determine what to show per state
        if (activityState instanceof AuthState) {
            handleAuthState();
        } else if (activityState instanceof FeedState) {
            handleFeedState();
        } else if (activityState instanceof SendMessageState) {
            handleSendMessageState();
        } else {
            throw new RuntimeException("This is impossible to happen, but who knows.");
        }
    }

    private void handleAuthState() {
        // TODO
    }

    private void handleFeedState() {
        // TODO
    }

    private void handleSendMessageState() {
        // TODO
    }

    ///////////////////////////////////////

    // Tests for HTTP API

    private void testMockServices() {
        final Context context = this;
        final com.testfairy.sniff_her.mock.api.AuthEndpoint authEndpoint = new com.testfairy.sniff_her.mock.api.AuthEndpoint();
        final com.testfairy.sniff_her.mock.api.DogEndpoint dogEndpoint = new com.testfairy.sniff_her.mock.api.DogEndpoint();
        final com.testfairy.sniff_her.mock.api.OwnerEndpoint ownerEndpoint = new com.testfairy.sniff_her.mock.api.OwnerEndpoint();

        // Auth test
        authEndpoint.authenticate(context, new Credentials("diego", "perini"))
                .subscribe(new Consumer<AuthenticationToken>() {
                    @Override
                    public void accept(AuthenticationToken authenticationToken) throws Exception {
                        Log.d("Test", authenticationToken.toString());
                    }
                });

        ///////////// WARNING!/////////////
        // Below usage of Rx is actually bad practice but this is acceptable for a very simple case.
        // Don't try this at home. (don't subscribe inside another subscription, use flatMap instead)

        // Feed test
        dogEndpoint.getDogs(context)
                .subscribe(new Consumer<List<Dog>>() {
                    @Override
                    public void accept(List<Dog> dogs) throws Exception {
                        Log.d("Test", dogs.toString());

                        // Swipe right test
                        dogEndpoint.markDogAsInterested(context, dogs.get(0))
                                .subscribe(new Consumer<Dog>() {
                                    @Override
                                    public void accept(Dog dog) throws Exception {
                                        Log.d("Test", dog.toString());
                                    }
                                });

                        // Swipe left test
                        dogEndpoint.markDogAsNotInterested(context, dogs.get(1))
                                .subscribe(new Consumer<Dog>() {
                                    @Override
                                    public void accept(Dog dog) throws Exception {
                                        Log.d("Test", dog.toString());
                                    }
                                });

                        // Send message to owner test
                        ownerEndpoint.sendMessage(context, dogs.get(0).getOwner(), new Message("Hello friend!"))
                                .subscribe(new Consumer<Owner>() {
                                    @Override
                                    public void accept(Owner owner) throws Exception {
                                        Log.d("Test", owner.toString());
                                    }
                                });
                    }
                });
    }
}
