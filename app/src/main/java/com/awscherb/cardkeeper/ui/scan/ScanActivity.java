package com.awscherb.cardkeeper.ui.scan;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.awscherb.cardkeeper.R;
import com.awscherb.cardkeeper.ui.base.BaseActivity;

public class ScanActivity extends BaseActivity {

    // ========================================================================
    // Lifecycle methods
    // ========================================================================

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scan);
        insertFragment(new ScanFragment());
    }
}
