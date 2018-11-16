package com.testfairy.sniff_her;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.testfairy.sniff_her.api.AuthEndpoint;
import com.testfairy.sniff_her.api.DogEndpoint;
import com.testfairy.sniff_her.api.OwnerEndpoint;
import com.testfairy.sniff_her.entity.AuthenticationToken;
import com.testfairy.sniff_her.entity.Credentials;
import com.testfairy.sniff_her.entity.Dog;
import com.testfairy.sniff_her.entity.Message;
import com.testfairy.sniff_her.entity.Owner;
import com.testfairy.sniff_her.utility.HttpUtility;
import com.testfairy.sniff_her.utility.ObjectUtil;
import com.testfairy.sniff_her.utility.StringUtil;
import com.testfairy.sniff_her.view.AuthenticationView;
import com.testfairy.sniff_her.view.SendMessageView;
import com.testfairy.sniff_her.view.SlidingView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

////////////////////// READ THIS ////////////////////////////////////////////////////////////////////////
//
// This Activity is a simple state machine.
//
// Each state has its own views and data.
//
// Each state is responsible for finding and caching its views.
//
// Each state is responsible for storing its data.
//
// Activity decides when to change states at the end of a executed job.
// Observable classes of Rx library is used for asynchronous job orchestration.
// In other words, a job is an Observable subscription.
//
// Look for methods that looks like "handleAuthState", "handleFeedState" etc
// to understand what each state is responsible for.
//
// Look for "prepareEndpoints()" to disable mock data when server is ready.
//
//////////////////////////////////////////////////////////////////////////////////////////////////////////
public class MainActivity extends AppCompatActivity {

    // Interface shared by all states of the activity
    private interface MainActivityState {
        boolean haveMissingViews();
        void findAndStoreViews(ViewGroup root);
        void hideViews();
        void showViews();
    }

    // Authentication activity state
    private static class AuthState implements MainActivityState {
        public View root;
        public AuthenticationView authenticationView;
        public Credentials credentials;

        public AuthState(Credentials credentials) {
            this.credentials = credentials;
        }

        public AuthState(Credentials credentials, AuthState previous) {
            this.credentials = credentials;
            this.root = previous.root;
            this.authenticationView = previous.authenticationView;
        }

        @Override
        public boolean haveMissingViews() {
            return !ObjectUtil.allNotNull(authenticationView, root);
        }

        @Override
        public void findAndStoreViews(ViewGroup root) {
            this.root = root.findViewById(R.id.auth_state_view);
            this.authenticationView = root.findViewById(R.id.authentication_view);
        }

        @Override
        public void hideViews() {
            root.setVisibility(View.GONE);
        }

        @Override
        public void showViews() {
            root.setVisibility(View.VISIBLE);
        }
    }

    // Dog feed activity state
    private static class FeedState implements MainActivityState {
        public View root;
        public SlidingView slidingView;
        public Stack<Dog> dogList;

        public FeedState(List<Dog> dogList) {
            this.dogList = new Stack<>();
            this.dogList.addAll(dogList);
        }

        public FeedState(List<Dog> dogList, FeedState previous) {
            this.dogList = new Stack<>();
            this.dogList.addAll(dogList);

            this.root = previous.root;
            this.slidingView = previous.slidingView;
        }

        @Override
        public boolean haveMissingViews() {
            return !ObjectUtil.allNotNull(slidingView, root);
        }

        @Override
        public void findAndStoreViews(ViewGroup root) {
            this.root = root.findViewById(R.id.feed_state_view);
            this.slidingView = root.findViewById(R.id.sliding_view);
        }

        @Override
        public void hideViews() {
            root.setVisibility(View.GONE);
        }

        @Override
        public void showViews() {
            root.setVisibility(View.VISIBLE);
        }
    }

    // Send message to a dog owner activity state
    private static class SendMessageState implements MainActivityState {
        public View root;
        public SendMessageView sendMessageView;
        public FeedState stateToReturn;
        public Dog dog;
        public Message message;

        public SendMessageState(FeedState stateToReturn, Dog dog, Message message) {
            this.stateToReturn = stateToReturn;
            this.dog = dog;
            this.message = message;
        }

        @Override
        public boolean haveMissingViews() {
            return !ObjectUtil.allNotNull(sendMessageView, root);
        }

        @Override
        public void findAndStoreViews(ViewGroup root) {
            this.root = root.findViewById(R.id.send_message_state_view);
            this.sendMessageView = root.findViewById(R.id.send_message_view);
        }

        @Override
        public void hideViews() {
            root.setVisibility(View.GONE);
        }

        @Override
        public void showViews() {
            root.setVisibility(View.VISIBLE);
        }
    }

    ///////////////////////////////////////

    // Activity Properties

    private View goodbyeView;
    private MainActivityState activityState; // Current activity state
    private AuthEndpoint authEndpoint; // API for sign-in
    private DogEndpoint dogEndpoint; // API for getting dogs and marking them as interested
    private OwnerEndpoint ownerEndpoint; // API for sending messages to dog owners

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Prepare request queue
        HttpUtility.getInstance(this);

        // Set initial state as Auth
        activityState = new AuthState(new Credentials("", ""));
        goodbyeView = findViewById(R.id.goodbye_view);

        prepareEndpoints();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Prepare state again just in case they lost persistence for views, resources etc
        prepareStates();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Reset states if they are in the middle of an action
        resetStates();
    }

    // TODO : Endpoint Setup, change below lines with real implementations once the server is ready
    private void prepareEndpoints() {
        // Fake Endpoints
        authEndpoint = new com.testfairy.sniff_her.mock.api.AuthEndpoint();
        dogEndpoint = new com.testfairy.sniff_her.mock.api.DogEndpoint();
        ownerEndpoint = new com.testfairy.sniff_her.mock.api.OwnerEndpoint();

        // Real Endpoints
//        authEndpoint = new AuthEndpoint();
//        dogEndpoint = new DogEndpoint();
//        ownerEndpoint = new OwnerEndpoint();
    }

    @NonNull
    private Context getActivityContext() {
        return this;
    }

    // State Orchestration

    private void prepareStates() {
        // Check current state if it knows its own views. This should
        // only happen once per state type, 3 times per app lifetime
        // in this case.
        if (activityState.haveMissingViews()) {
            activityState.findAndStoreViews((ViewGroup) findViewById(R.id.rootView));
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

        activityState.showViews();
    }

    private void resetStates() {
        if (activityState instanceof AuthState) {

        } else if (activityState instanceof FeedState) {
            FeedState feed = (FeedState) activityState;
            feed.slidingView.reset();
        } else if (activityState instanceof SendMessageState) {

        } else {
            throw new RuntimeException("This is impossible to happen, but who knows.");
        }
    }

    private void setNewState(MainActivityState newState) {
        activityState.hideViews();
        activityState = newState;
        prepareStates();
    }

    // Auth State

    private AuthenticationView.OnSignInListener onSignInListener = new AuthenticationView.OnSignInListener() {
        @Override
        public void onSignIn(String username, String password) {
            final AuthState newAuthState;
            if (activityState instanceof AuthState) {
                newAuthState = new AuthState(new Credentials(username, password), (AuthState) activityState);
            } else {
                newAuthState = new AuthState(new Credentials(username, password));
            }

            if (StringUtil.allNotEmpty(newAuthState.credentials.getUsername(), newAuthState.credentials.getPassword())) {
                authEndpoint.authenticate(getActivityContext(), newAuthState.credentials)
                        .doOnError(new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(getActivityContext(), "Username or password is invalid!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .flatMap(new Function<AuthenticationToken, ObservableSource<List<Dog>>>() {
                            @Override
                            public ObservableSource<List<Dog>> apply(AuthenticationToken authenticationToken) throws Exception {
                                // TODO : something with the token like storing
                                // somewhere to inject into every next HTTP call.
                                return dogEndpoint.getDogs(getActivityContext());
                            }
                        })
                        .subscribe(new Observer<List<Dog>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(List<Dog> dogs) {
                                Toast.makeText(getActivityContext(), "Signed in!", Toast.LENGTH_SHORT).show();
                                setNewState(new FeedState(dogs));
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(getActivityContext(), "No dog available today!", Toast.LENGTH_SHORT).show();
                                setNewState(newAuthState);
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        }
    };

    private void handleAuthState() {
        AuthState auth = (AuthState) activityState;

        auth.authenticationView.setCredentials(auth.credentials.getUsername(), auth.credentials.getPassword());
        auth.authenticationView.setOnSignInListener(onSignInListener);
    }

    // Feed State

    private SlidingView.OnSlideListener onSlideListener = new SlidingView.OnSlideListener() {

        private void shrinkStack() {
            FeedState feed = (FeedState) activityState;
            feed.dogList.pop();
            if (!feed.dogList.empty()) setNewState(new FeedState(feed.dogList, feed));
            else goodbyeView.setVisibility(View.VISIBLE);
        }

        private Dog currentDog() {
            FeedState feed = (FeedState) activityState;
            return feed.dogList.peek();
        }

        @Override
        public void onLeftSlide() {
            dogEndpoint.markDogAsNotInterested(getActivityContext(), currentDog())
                    .subscribe(new Observer<Dog>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Dog dog) {
                            FeedState feed = (FeedState) activityState;
                            shrinkStack();
                            Toast.makeText(getActivityContext(), "Ignored!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(getActivityContext(), "Somethings wrong. Please try again!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }

        @Override
        public void onRightSlide() {
            dogEndpoint.markDogAsInterested(getActivityContext(), currentDog())
                    .subscribe(new Observer<Dog>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Dog dog) {
                            shrinkStack();
                            setNewState(new SendMessageState((FeedState) activityState, currentDog(), new Message("")));
                            Toast.makeText(getActivityContext(), "Liked!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(getActivityContext(), "Somethings wrong. Please try again!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        }
    };

    private void handleFeedState() {
        FeedState feed = (FeedState) activityState;

        feed.slidingView.setDetails(feed.dogList.peek(), feed.dogList.peek().getOwner());
        feed.slidingView.setOnSlideListener(onSlideListener);
    }

    // Send Message State
    private SendMessageView.OnSendListener onSendListener = new SendMessageView.OnSendListener() {

        private Dog currentDog() {
            SendMessageState send = (SendMessageState) activityState;
            return send.dog;
        }

        private boolean canContinueSliding() {
            SendMessageState send = (SendMessageState) activityState;
            FeedState feed = send.stateToReturn;
            return feed.dogList.size() > 0;
        }

        private MainActivityState nextState() {
            SendMessageState send = (SendMessageState) activityState;
            return send.stateToReturn;
        }

        @Override
        public void onSend(String message) {
            if (StringUtil.allNotEmpty(message)) {
                ownerEndpoint.sendMessage(getActivityContext(), currentDog().getOwner(), new Message(message))
                        .subscribe(new Observer<Owner>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Owner owner) {
                                if (canContinueSliding()) {
                                    setNewState(nextState());
                                } else {
                                    goodbyeView.setVisibility(View.VISIBLE);
                                }
                                Toast.makeText(getActivityContext(), "Sent!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(getActivityContext(), "Somethings wrong. Please try again!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        }
    };

    private void handleSendMessageState() {
        SendMessageState sendMessage = (SendMessageState) activityState;

        sendMessage.sendMessageView.setMessage(sendMessage.message);
        sendMessage.sendMessageView.setOnSendListener(onSendListener);
    }

}
