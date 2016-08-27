package com.awscherb.cardkeeper.ui.controller.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.awscherb.cardkeeper.R;
import com.awscherb.cardkeeper.data.CoreData;
import com.awscherb.cardkeeper.model.ScannedCode;
import com.awscherb.cardkeeper.ui.adapter.ScannedCodeAdapter;
import com.awscherb.cardkeeper.ui.controller.base.CKActivity;
import com.awscherb.cardkeeper.ui.controller.scan.ScanActivity;
import com.google.zxing.BarcodeFormat;

public class MainActivity extends CKActivity {

    public static final int REQUEST_GET_CODE = 3;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ScannedCodeAdapter adapter;
    private FloatingActionButton fab;

    // ========================================================================
    // Lifecycle methods
    // ========================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtain handles to UI elements
        recyclerView = (RecyclerView) findViewById(R.id.activity_main_recycler);
        fab = (FloatingActionButton) findViewById(R.id.activity_main_fab);

        // Setup the recycler stuff
        adapter = new ScannedCodeAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        // Scan button
        fab.setOnClickListener(v -> startActivityForResult(
                new Intent(this, ScanActivity.class), REQUEST_GET_CODE));

        CoreData.getScannedCodeService().listAllScannedCodes()
                .compose(bindToLifecycle())
                .subscribe(adapter::swapObjects);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_GET_CODE && resultCode == RESULT_OK) {
            String text = data.getStringExtra(ScanActivity.EXTRA_BARCODE_TEXT);
            BarcodeFormat format = (BarcodeFormat) data.getSerializableExtra(ScanActivity.EXTRA_BARCODE_FORMAT);
            addCode(text, format);
        }
    }

    // ========================================================================
    // Helper methods
    // ========================================================================

    private void addCode(String text, BarcodeFormat format) {
        ScannedCode code = new ScannedCode();
        code.setId(System.currentTimeMillis());
        code.setText(text);
        code.setFormat(format);

        CoreData.getScannedCodeService().addScannedCode(code)
                .compose(bindToLifecycle())
                .subscribe(addedCode -> Log.i("MainActivity", "Code added"),
                        Throwable::printStackTrace);

    }
}
