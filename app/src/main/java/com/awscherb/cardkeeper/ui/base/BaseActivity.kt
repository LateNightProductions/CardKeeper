package com.awscherb.cardkeeper.ui.base


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.Toolbar

import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.di.component.ViewComponent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

open class BaseActivity : RxAppCompatActivity() {

    private var toolbar: Toolbar? = null

    //================================================================================
    // Lifecycle methods
    //================================================================================

    protected fun insertFragment(fragment: BaseFragment) {
        val fragmentManager = supportFragmentManager
        val fragmentContainer = fragmentManager.findFragmentById(R.id.container)

        if (fragmentContainer == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit()
        }
    }

    //================================================================================
    // Helper methods
    //================================================================================

    fun setUpToolbar() {
        val t = findViewById<Toolbar>(R.id.toolbar)
        toolbar = t

        setSupportActionBar(toolbar)
    }

    fun setupToolbarBack() {
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun setTitle(title: String) {
        toolbar!!.title = title
    }

    fun viewComponent(): ViewComponent = (application as BaseApplication).viewComponent

}
