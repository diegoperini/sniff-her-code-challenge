package com.testfairy.sniff_her.mock.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.testfairy.sniff_her.entity.Message;
import com.testfairy.sniff_her.entity.Owner;
import com.testfairy.sniff_her.mock.ObservableMock;

import io.reactivex.Observable;

public class OwnerEndpoint extends com.testfairy.sniff_her.api.OwnerEndpoint {

    @Override
    public Observable<Owner> sendMessage(@NonNull Context context, @NonNull Owner owner, @NonNull Message message) {
        return ObservableMock.mock(super.sendMessage(context, owner, message), Owner.class);
    }
    
}
