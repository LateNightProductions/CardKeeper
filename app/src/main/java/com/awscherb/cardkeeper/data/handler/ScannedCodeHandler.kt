package com.awscherb.cardkeeper.data.handler

import com.awscherb.cardkeeper.data.dao.ScannedCodeDao
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class ScannedCodeHandler @Inject constructor(
        @Inject @JvmField var scannedCodeDao: ScannedCodeDao)
    : BaseHandler(), ScannedCodeService {

    override fun getScannedCode(codeId: Int): Single<ScannedCode> {
        return scannedCodeDao.getScannedCode(codeId).compose(scheduleSingle())
    }

    override fun listAllScannedCodes(): Flowable<List<ScannedCode>> {
        return scannedCodeDao.listScannedCodes().compose(scheduleFlowable())
    }

    override fun addScannedCode(scannedCode: ScannedCode): Single<ScannedCode> {
        return Single.fromCallable {
            scannedCodeDao.insertCode(scannedCode)
            scannedCode
        }.compose(scheduleSingle())
    }

    override fun updateScannedCode(scannedCode: ScannedCode): Single<ScannedCode> {
        return Single.fromCallable {
            scannedCodeDao.updateCode(scannedCode)
            scannedCode
        }.compose(scheduleSingle())
    }

    override fun deleteScannedCode(scannedCode: ScannedCode): Completable {
        return Completable.fromCallable {
            scannedCodeDao.deleteCode(scannedCode)
        }.compose(scheduleCompletable())
    }

}