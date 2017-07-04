package com.awscherb.cardkeeper.data.handler;

import com.awscherb.cardkeeper.data.model.ScannedCode;
import com.awscherb.cardkeeper.data.service.ScannedCodeService;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;


public class RealmScannedCodeHandler extends BaseRealmHandler<ScannedCode> implements ScannedCodeService {

    @Override
    public Class<ScannedCode> getModelClass() {
        return ScannedCode.class;
    }


    @Override
    public Single<ScannedCode> getScannedCode(long codeId) {
        return getObject(codeId);
    }

    @Override
    public Observable<List<ScannedCode>> listAllScannedCodes() {
        return listObjects();
    }

    @Override
    public Single<ScannedCode> addScannedCode(ScannedCode scannedCode) {
        return createObject(scannedCode);
    }

    @Override
    public Single<ScannedCode> updateScannedCode(ScannedCode scannedCode) {
        return updateObject(scannedCode);
    }

    @Override
    public Completable deleteScannedCode(ScannedCode scannedCode) {
        return deleteObject(scannedCode);
    }
}
