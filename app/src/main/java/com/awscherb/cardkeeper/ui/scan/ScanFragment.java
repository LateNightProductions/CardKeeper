package com.awscherb.cardkeeper.ui.scan;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awscherb.cardkeeper.R;
import com.awscherb.cardkeeper.ui.base.BaseFragment;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ScanFragment extends BaseFragment {

    private static final String TAG = ScanFragment.class.getName();

    public static final String EXTRA_BARCODE_FORMAT = TAG + ".extra_barcode_format";
    public static final String EXTRA_BARCODE_TEXT = TAG + ".extra_barcode_text";

    private static final int REQUEST_CAMERA = 16;

    private CompoundBarcodeView barcodeView;

    //================================================================================
    // Lifecycle methods
    //================================================================================

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scan, container, false);
        if (v == null) {
            return null;
        }

        barcodeView = (CompoundBarcodeView) v.findViewById(R.id.fragment_scan_scanner);

        // Check permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PermissionChecker.PERMISSION_GRANTED) {
                barcodeView.resume();
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA);
            }
        }

        // Setup scanner
        setCallback();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    // ========================================================================
    // Helper methods
    // ========================================================================

    private void setCallback() {
        BarcodeCallback callback = new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {

                String text = "";
                if (result.getText() != null) {
                    text = result.getText();
                }

                Intent data = new Intent();
                data.putExtra(EXTRA_BARCODE_FORMAT, result.getBarcodeFormat());
                data.putExtra(EXTRA_BARCODE_TEXT, text);
                getActivity().setResult(RESULT_OK, data);
                getActivity().finish();
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {
            }
        };

        barcodeView.decodeContinuous(callback);
    }
}
