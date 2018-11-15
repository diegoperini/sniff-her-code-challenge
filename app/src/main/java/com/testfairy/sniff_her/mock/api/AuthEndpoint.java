package com.testfairy.sniff_her.mock.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.testfairy.sniff_her.entity.AuthenticationToken;
import com.testfairy.sniff_her.entity.Credentials;
import com.testfairy.sniff_her.mock.ObservableMock;

import io.reactivex.Observable;

public class AuthEndpoint extends com.testfairy.sniff_her.api.AuthEndpoint {

    @Override
    public Observable<AuthenticationToken> authenticate(@NonNull Context context, @NonNull Credentials credentials) {
        return ObservableMock.mock(super.authenticate(context, credentials), AuthenticationToken.class);
    }

}
