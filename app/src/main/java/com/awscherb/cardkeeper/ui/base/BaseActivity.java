package com.awscherb.cardkeeper.ui.base;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import com.awscherb.cardkeeper.R;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

public class BaseActivity extends RxAppCompatActivity {

    private Toolbar toolbar;

    //================================================================================
    // Lifecycle methods
    //================================================================================

    protected void insertFragment(BaseFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragmentContainer = fragmentManager.findFragmentById(R.id.container);

        if (fragmentContainer == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

    //================================================================================
    // Helper methods
    //================================================================================

    public void setUpToolbar() {
        Toolbar t = (Toolbar) findViewById(R.id.toolbar);
        toolbar = t;

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

}
