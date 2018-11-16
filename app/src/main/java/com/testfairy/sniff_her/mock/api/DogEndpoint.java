package com.testfairy.sniff_her.mock.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.testfairy.sniff_her.entity.Dog;
import com.testfairy.sniff_her.mock.ObservableMock;

import java.util.List;

import io.reactivex.Observable;

public class DogEndpoint extends com.testfairy.sniff_her.api.DogEndpoint {

    @NonNull
    @Override
    public Observable<List<Dog>> getDogs(@NonNull final Context context) {
        return ObservableMock.mockList(super.getDogs(context), Dog.class);
    }

    @NonNull
    @Override
    public Observable<Dog> markDogAsInterested(@NonNull final Context context, @NonNull final Dog dog) {
        return ObservableMock.mock(super.markDogAsInterested(context, dog), Dog.class);
    }

    @NonNull
    @Override
    public Observable<Dog> markDogAsNotInterested(@NonNull final Context context, @NonNull final Dog dog) {
        return ObservableMock.mock(super.markDogAsNotInterested(context, dog), Dog.class);
    }

}
