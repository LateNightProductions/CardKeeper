package com.awscherb.cardkeeper.data.handler;

import com.awscherb.cardkeeper.data.model.BaseModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;


abstract class BaseHandler<T extends BaseModel> {

    protected static final String FIELD_ID = "id";

    //================================================================================
    // Get
    //================================================================================

    abstract Single<T> getObject(long id);

    abstract Observable<List<T>> listObjects();

    //================================================================================
    // Create
    //================================================================================

    abstract Single<T> createObject(T object);

    //================================================================================
    // Update
    //================================================================================

    abstract Single<T> updateObject(T object);

    //================================================================================
    // Delete
    //================================================================================

    abstract Completable deleteObject(T object);

}
