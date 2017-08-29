package com.awscherb.cardkeeper.ui.base


import com.trello.rxlifecycle2.components.support.RxFragment

abstract class BaseFragment : RxFragment() {

    protected val baseActivity: BaseActivity
        get() = activity as BaseActivity

}
