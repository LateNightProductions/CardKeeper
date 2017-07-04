package com.awscherb.cardkeeper.data.service;


import com.awscherb.cardkeeper.data.model.ScannedCode;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;


public interface ScannedCodeService {

    Single<ScannedCode> getScannedCode(long codeId);
    Observable<List<ScannedCode>> listAllScannedCodes();

    Single<ScannedCode> addScannedCode(ScannedCode scannedCode);

    Single<ScannedCode> updateScannedCode(ScannedCode scannedCode);

    Completable deleteScannedCode(ScannedCode scannedCode);

}
