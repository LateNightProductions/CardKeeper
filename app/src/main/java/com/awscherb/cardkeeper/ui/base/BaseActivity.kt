package com.awscherb.cardkeeper.ui.base

import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity() {

    fun viewComponent() = (application as CardKeeperApplication).viewComponent

}
