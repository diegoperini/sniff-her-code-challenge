package com.testfairy.sniff_her.mock.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.testfairy.sniff_her.entity.Dog;
import com.testfairy.sniff_her.mock.Mock;

import java.util.List;

import io.reactivex.Observable;

public class DogEndpoint extends com.testfairy.sniff_her.api.DogEndpoint {

    @Override
    public Observable<List<Dog>> getDogs(@NonNull Context context, @NonNull Dog dog) {
        return Mock.mockSingle(super.getDogs(context, dog));
    }

    @Override
    public Observable<Dog> markDogAsInterested(@NonNull Context context, @NonNull Dog dog) {
        return Mock.mockSingle(super.markDogAsInterested(context, dog));
    }

    @Override
    public Observable<Dog> markDogAsNotInterested(@NonNull Context context, @NonNull Dog dog) {
        return Mock.mockSingle(super.markDogAsNotInterested(context, dog));
    }

}
