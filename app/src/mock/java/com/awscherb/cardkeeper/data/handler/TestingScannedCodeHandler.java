package com.awscherb.cardkeeper.data.handler;

import com.awscherb.cardkeeper.data.model.ScannedCode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;

public class TestingScannedCodeHandler {

    private static ConcurrentHashMap<Long, ScannedCode> scannedCodeTable
            = new ConcurrentHashMap<>();

    public Observable<ScannedCode> getScannedCode(long codeId) {
        return Observable.just(scannedCodeTable.get(codeId));
    }

    public Observable<List<ScannedCode>> listAllScannedCodes() {
        return Observable.just(new ArrayList<>(scannedCodeTable.values()));
    }

    public Observable<ScannedCode> addScannedCode(ScannedCode scannedCode) {
        scannedCodeTable.put(scannedCode.getId(), scannedCode);
        return null;
    }

    public Observable<ScannedCode> updateScannedCode(ScannedCode scannedCode) {
        return Observable.just(scannedCodeTable.put(scannedCode.getId(), scannedCode));
    }

    public Observable<Void> deleteScannedCode(ScannedCode scannedCode) {
        scannedCodeTable.remove(scannedCode.getId());
        return null;
    }
}
