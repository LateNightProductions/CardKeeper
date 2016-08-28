package com.awscherb.cardkeeper.data.handler;

import com.awscherb.cardkeeper.model.BaseModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.exceptions.RealmException;
import rx.AsyncEmitter;
import rx.Observable;
import rx.functions.Action1;

public abstract class BaseRealmHandler<T extends RealmObject & BaseModel> extends BaseHandler<T> {

    private final Realm realm;

    public BaseRealmHandler(Realm realm) {
        this.realm = realm;
    }

    public abstract Class<T> getModelClass();

    public Observable<T> doAsync(T object, Action1<T> action1) {
        return Observable.fromAsync(e -> {
                    realm.beginTransaction();
                    try {
                        action1.call(object);
                        realm.commitTransaction();
                    } catch (RuntimeException exception) {
                        realm.cancelTransaction();
                        e.onError(new RealmException("Error during transaction.", exception));
                        return;
                    } catch (Error error) {
                        realm.cancelTransaction();
                        e.onError(error);
                        return;
                    }
                    e.onNext(object);
                    e.onCompleted();
                },
                AsyncEmitter.BackpressureMode.BUFFER);
    }

    //================================================================================
    // Get
    //================================================================================

    @Override
    public Observable<T> getObject(String id) {
        return realm.where(getModelClass()).equalTo("id", id).findAll().first().asObservable();
    }

    @Override
    public Observable<List<T>> listObjects() {
        return realm.where(getModelClass()).findAll().asObservable().map(realm::copyFromRealm);

    }

    //================================================================================
    // Create
    //================================================================================

    @Override
    public Observable<T> createObject(T object) {
        return doAsync(object, realm::copyToRealmOrUpdate);
    }

    //================================================================================
    // Update
    //================================================================================

    @Override
    public Observable<T> updateObject(T object) {
        return doAsync(object, realm::copyToRealmOrUpdate);
    }

    //================================================================================
    // Delete
    //================================================================================

    @Override
    public Observable<Void> deleteObject(T object) {
        return doAsync(object, o ->
                realm.where(getModelClass())
                        .equalTo("id", o.getId())
                        .findAll()
                        .first()
                        .deleteFromRealm())
                .map(delete -> null);

    }

}
