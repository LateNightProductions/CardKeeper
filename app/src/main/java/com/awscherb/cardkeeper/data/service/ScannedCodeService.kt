package com.awscherb.cardkeeper.data.service


import com.awscherb.cardkeeper.data.model.ScannedCode

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


interface ScannedCodeService {

    fun getScannedCode(codeId: Int): Single<ScannedCode>
    fun listAllScannedCodes(): Flowable<List<ScannedCode>>

    fun addScannedCode(scannedCode: ScannedCode): Single<ScannedCode>

    fun updateScannedCode(scannedCode: ScannedCode): Single<ScannedCode>

    fun deleteScannedCode(scannedCode: ScannedCode): Completable

}
