package com.awscherb.cardkeeper.data.handler;

import com.awscherb.cardkeeper.model.BaseModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.exceptions.RealmException;
import rx.Observable;
import rx.functions.Action1;

public abstract class BaseRealmHandler<T extends RealmObject & BaseModel> extends BaseHandler<T> {

    private final Realm realm;

    public BaseRealmHandler(Realm realm) {
        this.realm = realm;
    }

    public abstract Class<T> getModelClass();

    public Observable.OnSubscribe<T> doInTransaction(T object, Action1<T> action1) {
        return subscriber -> {
            realm.beginTransaction();
            try {
                action1.call(object);
                realm.commitTransaction();
            } catch (RuntimeException e) {
                realm.cancelTransaction();
                subscriber.onError(new RealmException("Error during transaction.", e));
                return;
            } catch (Error e) {
                realm.cancelTransaction();
                subscriber.onError(e);
                return;
            }
            subscriber.onNext(object);
        };
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
        return Observable.create(doInTransaction(object, realm::copyToRealm));
    }

    //================================================================================
    // Update
    //================================================================================

    @Override
    public Observable<T> updateObject(T object) {
        return Observable.create(doInTransaction(object, realm::copyToRealmOrUpdate));
    }

    //================================================================================
    // Delete
    //================================================================================

    @Override
    public Observable<Void> deleteObject(T object) {
        return Observable.create(doInTransaction(object, o -> {
            realm.where(getModelClass()).equalTo("id", o.getId())
                    .findAll().first().deleteFromRealm();
        })).map(deleted -> null);
    }

}
