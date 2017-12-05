package com.awscherb.cardkeeper.di.module

import com.awscherb.cardkeeper.data.handler.ScannedCodeHandler
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import dagger.Binds
import dagger.Module
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

@Module
abstract class ServiceModule {
    @Binds abstract fun bindScannedCodeService(handler: MockScannedCodeService): ScannedCodeService
}

class MockScannedCodeService @Inject constructor() : ScannedCodeService {

    companion object {
        var codes: MutableMap<Int, ScannedCode> = HashMap()
    }

    override fun getScannedCode(codeId: Int) = Single.just(codes[codeId] ?: ScannedCode())

    override fun listAllScannedCodes(): Flowable<List<ScannedCode>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addScannedCode(scannedCode: ScannedCode): Single<ScannedCode> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateScannedCode(scannedCode: ScannedCode): Single<ScannedCode> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteScannedCode(scannedCode: ScannedCode): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}