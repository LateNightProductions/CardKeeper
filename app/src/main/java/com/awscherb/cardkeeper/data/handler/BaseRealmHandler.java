package com.awscherb.cardkeeper.data.handler;

import com.awscherb.cardkeeper.data.model.BaseModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.exceptions.RealmException;
import rx.Emitter;
import rx.Observable;
import rx.functions.Action1;

abstract class BaseRealmHandler<T extends RealmObject & BaseModel> extends BaseHandler<T> {

    public abstract Class<T> getModelClass();

    private Observable<T> doAsync(T object, Realm realm, Action1<T> action1) {
        return Observable.fromEmitter(e -> {
                    realm.beginTransaction();
                    try {
                        action1.call(object);
                        realm.commitTransaction();
                    } catch (RuntimeException exception) {
                        realm.cancelTransaction();
                        e.onError(new RealmException("Error during transaction.", exception));
                    } catch (Error error) {
                        realm.cancelTransaction();
                        e.onError(error);
                    } finally {
                        realm.close();
                    }
                    e.onNext(object);
                    e.onCompleted();
                },
                Emitter.BackpressureMode.BUFFER);
    }

    //================================================================================
    // Get
    //================================================================================

    @Override
    public Observable<T> getObject(long id) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(getModelClass()).equalTo("id", id).findAll()
                .asObservable()
                .flatMapIterable(object -> object)
                .map(realm::copyFromRealm)
                .doOnUnsubscribe(realm::close);
    }

    @Override
    public Observable<List<T>> listObjects() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(getModelClass()).findAll()
                .asObservable()
                .map(realm::copyFromRealm)
                .doOnUnsubscribe(realm::close);
    }

    //================================================================================
    // Create
    //================================================================================

    @Override
    public Observable<T> createObject(T object) {
        Realm realm = Realm.getDefaultInstance();
        return doAsync(object, realm, realm::copyToRealmOrUpdate);
    }

    //================================================================================
    // Update
    //================================================================================

    @Override
    public Observable<T> updateObject(T object) {
        Realm realm = Realm.getDefaultInstance();
        return doAsync(object, realm,  realm::copyToRealmOrUpdate);
    }

    //================================================================================
    // Delete
    //================================================================================

    @Override
    public Observable<Void> deleteObject(T object) {
        Realm realm = Realm.getDefaultInstance();
        return doAsync(object, realm, o ->
                realm.where(getModelClass())
                        .equalTo("id", o.getId())
                        .findAll()
                        .first()
                        .deleteFromRealm())
                .map(delete -> null);

    }

}
