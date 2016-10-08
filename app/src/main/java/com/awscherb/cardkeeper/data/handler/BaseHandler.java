package com.awscherb.cardkeeper.data.handler;

import com.awscherb.cardkeeper.data.model.BaseModel;

import java.util.List;

import rx.Observable;


abstract class BaseHandler<T extends BaseModel> {

    //================================================================================
    // Get
    //================================================================================

    abstract Observable<T> getObject(long id);

    abstract Observable<List<T>> listObjects();

    //================================================================================
    // Create
    //================================================================================

    abstract Observable<T> createObject(T object);

    //================================================================================
    // Update
    //================================================================================

    abstract Observable<T> updateObject(T object);

    //================================================================================
    // Delete
    //================================================================================

    abstract Observable<Void> deleteObject(T object);

}
