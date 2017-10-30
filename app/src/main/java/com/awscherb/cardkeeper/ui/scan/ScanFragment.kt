package com.awscherb.cardkeeper.ui.scan

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.content.PermissionChecker
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.ui.base.BaseFragment
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView

import butterknife.BindView
import butterknife.ButterKnife

import android.app.Activity.RESULT_OK

class ScanFragment : BaseFragment() {

    companion object {
        private const val TAG = "ScanFragment"
        const val EXTRA_BARCODE_FORMAT = TAG + ".extra_barcode_format"
        const val EXTRA_BARCODE_TEXT = TAG + ".extra_barcode_text"

        private const val REQUEST_CAMERA = 16
    }

    @BindView(R.id.fragment_scan_scanner) internal lateinit var barcodeView: CompoundBarcodeView

    //================================================================================
    // Lifecycle methods
    //================================================================================

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_scan, container, false)
        ButterKnife.bind(this, v)

        // Check permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity!!.checkSelfPermission(Manifest.permission.CAMERA) == PermissionChecker.PERMISSION_GRANTED) {
                barcodeView.resume()
            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA),
                        REQUEST_CAMERA)
            }
        }

        // Setup scanner
        setCallback()

        return v
    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }

    // ========================================================================
    // Helper methods
    // ========================================================================

    private fun setCallback() {
        val callback = object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult) {
                val data = Intent().apply {
                    putExtra(EXTRA_BARCODE_FORMAT, result.barcodeFormat)
                    putExtra(EXTRA_BARCODE_TEXT, result.text ?: "")
                }

                with(activity!!) {
                    setResult(RESULT_OK, data)
                    finish()
                }
            }

            override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
        }

        barcodeView.decodeContinuous(callback)
    }


}
