package com.awscherb.cardkeeper.data.service;


import com.awscherb.cardkeeper.data.model.ScannedCode;

import java.util.List;

import rx.Observable;

public interface ScannedCodeService {

    Observable<ScannedCode> getScannedCode(long codeId);
    Observable<List<ScannedCode>> listAllScannedCodes();

    Observable<ScannedCode> addScannedCode(ScannedCode scannedCode);

    Observable<ScannedCode> updateScannedCode(ScannedCode scannedCode);

    Observable<Void> deleteScannedCode(ScannedCode scannedCode);

}
