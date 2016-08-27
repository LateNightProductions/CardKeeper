package com.awscherb.cardkeeper.ui.controller.scan;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.awscherb.cardkeeper.R;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.util.Arrays;
import java.util.List;

public class ScanActivity extends AppCompatActivity {

    private static final String TAG = ScanActivity.class.getName();

    public static final String EXTRA_BARCODE_FORMAT = TAG + ".extra_barcode_format";
    public static final String EXTRA_BARCODE_TEXT = TAG + ".extra_barcode_text";

    private static final int REQUEST_CAMERA = 16;

    private CompoundBarcodeView barcodeView;
    private BarcodeCallback callback;

    // ========================================================================
    // Lifecycle methods
    // ========================================================================

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scan);
        barcodeView = (CompoundBarcodeView) findViewById(R.id.barcode_scanner);

        // Check permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PermissionChecker.PERMISSION_GRANTED) {
                barcodeView.resume();
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA);
            }
        }

        // Setup scanner
        setCallback();
    }


    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    // ========================================================================
    // Helper methods
    // ========================================================================

    /**
     * Setup the callback for continuous decoding
     */
    private void setCallback() {
        callback = new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {

                Log.d("ScanActivityResult", Arrays.toString(result.getRawBytes()));
                String text = "";
                if (result.getText() != null) {
                    Log.d("ScanActivityResult ",result.getText());
                    text = result.getText();
                }

                Intent data = new Intent();
                data.putExtra(EXTRA_BARCODE_FORMAT, result.getBarcodeFormat());
                data.putExtra(EXTRA_BARCODE_TEXT, text);
                setResult(RESULT_OK, data);
                finish();
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {
            }
        };

        barcodeView.decodeContinuous(callback);
    }
}
