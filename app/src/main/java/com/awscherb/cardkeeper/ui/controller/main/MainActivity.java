package com.awscherb.cardkeeper.ui.controller.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.widget.EditText;

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

        // List all codes
        CoreData.getScannedCodeService().listAllScannedCodes()
                .compose(bindToLifecycle())
                .subscribe(adapter::swapObjects);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_GET_CODE && resultCode == RESULT_OK) {
            ScannedCode scannedCode = new ScannedCode();
            scannedCode.setId(System.currentTimeMillis());
            scannedCode.setText(data.getStringExtra(ScanActivity.EXTRA_BARCODE_TEXT));
            scannedCode.setFormat((BarcodeFormat) data.getSerializableExtra(ScanActivity.EXTRA_BARCODE_FORMAT));

            EditText input = new EditText(this);
            input.setHint(R.string.dialog_card_name_hint);
            input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);

            new AlertDialog.Builder(this)
                    .setTitle(R.string.app_name)
                    .setView(input)
                    .setPositiveButton(R.string.action_add,
                            (dialog, which) -> {
                                scannedCode.setTitle(input.getText().toString());
                                addCode(scannedCode);
                            })
                    .setNegativeButton(R.string.action_cancel,
                            (dialog, which) -> dialog.dismiss())
                    .show();

        }
    }

    // ========================================================================
    // Helper methods
    // ========================================================================

    /**
     * Add the ScannedCode to the database
     * @param code the ScannedCode
     */
    private void addCode(ScannedCode code) {
        CoreData.getScannedCodeService().addScannedCode(code)
                .compose(bindToLifecycle())
                .subscribe(addedCode -> Snackbar.make(
                        getCurrentFocus(),
                        R.string.activity_main_card_add_success,
                        Snackbar.LENGTH_SHORT).show(),
                        e -> Snackbar.make(
                                getCurrentFocus(),
                                R.string.activity_main_card_add_failure,
                                Snackbar.LENGTH_SHORT).show());

    }
}
