package com.awscherb.cardkeeper.data.handler;

import com.awscherb.cardkeeper.data.model.BaseModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Consumer;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.exceptions.RealmException;

abstract class BaseRealmHandler<T extends RealmObject & BaseModel> extends BaseHandler<T> {

    public abstract Class<T> getModelClass();

    //================================================================================
    // Get
    //================================================================================

    @Override
    public Single<T> getObject(long id) {
        final Realm realm = Realm.getDefaultInstance();
        return singleReam(realm)
                .map(r -> r.where(getModelClass()).equalTo("id", id).findAll())
                .map(realm::copyFromRealm)
                .map(list -> list.get(0))
                .doOnDispose(realm::close);
    }

    @Override
    public Observable<List<T>> listObjects() {
        final Realm realm = Realm.getDefaultInstance();
        return observableRealm(realm)
                .map(r -> r.where(getModelClass()).findAll())
                .map(realm::copyFromRealm)
                .doOnDispose(realm::close);
    }

    //================================================================================
    // Create
    //================================================================================

    @Override
    public Single<T> createObject(T object) {
        Realm realm = Realm.getDefaultInstance();
        return doAsync(object, realm, realm::copyToRealmOrUpdate);
    }

    //================================================================================
    // Update
    //================================================================================

    @Override
    public Single<T> updateObject(T object) {
        Realm realm = Realm.getDefaultInstance();
        return doAsync(object, realm,  realm::copyToRealmOrUpdate);
    }

    //================================================================================
    // Delete
    //================================================================================

    @Override
    public Completable deleteObject(T object) {
        Realm realm = Realm.getDefaultInstance();
        return doAsync(object, realm, o ->
                realm.where(getModelClass())
                        .equalTo("id", o.getId())
                        .findAll()
                        .first()
                        .deleteFromRealm()).toCompletable();
    }

    //================================================================================
    // Helper
    //================================================================================

    private Single<T> doAsync(T object, Realm realm, Consumer<T> consumer) {
        return Single.create(e -> {
            realm.beginTransaction();
            try {
                consumer.accept(object);
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
            e.onSuccess(object);
        });
    }

    private Observable<Realm> observableRealm(Realm realm) {
        return Observable.create(emitter -> {
            RealmConfiguration realmConfiguration = realm.getConfiguration();
            Realm observableRealm = Realm.getInstance(realmConfiguration);

            final RealmChangeListener<Realm> listener = emitter::onNext;
            emitter.setDisposable(Disposables.fromRunnable(() -> {
                observableRealm.removeChangeListener(listener);
                observableRealm.close();
            }));
            observableRealm.addChangeListener(listener);
            emitter.onNext(observableRealm);
        });
    }

    private Single<Realm> singleReam(Realm realm) {
        return Single.create(emitter -> {
            RealmConfiguration realmConfiguration = realm.getConfiguration();
            Realm observableRealm = Realm.getInstance(realmConfiguration);

            final RealmChangeListener<Realm> listener = emitter::onSuccess;
            emitter.setDisposable(Disposables.fromRunnable(() -> {
                observableRealm.removeChangeListener(listener);
                observableRealm.close();
            }));
            observableRealm.addChangeListener(listener);
            emitter.onSuccess(observableRealm);
        });
    }

}
