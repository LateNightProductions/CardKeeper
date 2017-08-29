package com.awscherb.cardkeeper.util.extensions

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers.*
import org.intellij.lang.annotations.Flow


fun <T> Flowable<T>.toMainThread(): Flowable<T> = this.observeOn(mainThread())
fun <T> Observable<T>.toMainThread(): Observable<T> = this.observeOn(mainThread())
fun <T> Single<T>.toMainThread(): Single<T> = this.observeOn(mainThread())
fun Completable.toMainThread(): Completable = this.observeOn(mainThread())