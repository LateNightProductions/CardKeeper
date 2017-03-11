package com.awscherb.cardkeeper.data.handler;

import com.awscherb.cardkeeper.data.model.ScannedCode;
import com.awscherb.cardkeeper.data.service.ScannedCodeService;

import java.util.List;

import rx.Observable;


public class RealmScannedCodeHandler extends BaseRealmHandler<ScannedCode> implements ScannedCodeService {

    @Override
    public Class<ScannedCode> getModelClass() {
        return ScannedCode.class;
    }


    @Override
    public Observable<ScannedCode> getScannedCode(long codeId) {
        return getObject(codeId);
    }

    @Override
    public Observable<List<ScannedCode>> listAllScannedCodes() {
        return listObjects();
    }

    @Override
    public Observable<ScannedCode> addScannedCode(ScannedCode scannedCode) {
        return createObject(scannedCode);
    }

    @Override
    public Observable<ScannedCode> updateScannedCode(ScannedCode scannedCode) {
        return updateObject(scannedCode);
    }

    @Override
    public Observable<Void> deleteScannedCode(ScannedCode scannedCode) {
        return deleteObject(scannedCode);
    }
}
