package com.awscherb.cardkeeper.data.handler

import io.reactivex.CompletableTransformer
import io.reactivex.FlowableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.schedulers.Schedulers

abstract class BaseHandler {
    fun <T> scheduleSingle() = SingleTransformer<T, T> { s -> s.subscribeOn(Schedulers.io()) }
    fun scheduleCompletable() = CompletableTransformer { s -> s.subscribeOn(Schedulers.io()) }
    fun <T> scheduleFlowable() = FlowableTransformer<T,T> { s -> s.subscribeOn(Schedulers.io()) }
}