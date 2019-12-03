package com.awscherb.cardkeeper.ui.base


import com.trello.rxlifecycle3.components.support.RxAppCompatActivity

abstract class BaseActivity : RxAppCompatActivity() {


    fun viewComponent() = (application as CardKeeperApplication).viewComponent

}
