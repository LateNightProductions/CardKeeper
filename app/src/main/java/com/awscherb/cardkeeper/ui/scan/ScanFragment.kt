package com.awscherb.cardkeeper.ui.scan

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.PermissionChecker
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.ui.base.BaseFragment
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import kotlinx.android.synthetic.main.fragment_scan.*

class ScanFragment : BaseFragment() {

    //================================================================================
    // Lifecycle methods
    //================================================================================

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_scan, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Check permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity!!.checkSelfPermission(Manifest.permission.CAMERA) == PermissionChecker.PERMISSION_GRANTED) {
                scanScanner.resume()
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CAMERA
                )
            }
        }

        // Setup scanner
        setCallback()
    }

    override fun onResume() {
        super.onResume()
        scanScanner.resume()
    }

    override fun onPause() {
        super.onPause()
        scanScanner.pause()
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

                activity?.run {
                    setResult(RESULT_OK, data)
                    finish()
                }
            }

            override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
        }

        scanScanner.decodeContinuous(callback)
    }


    companion object {
        private const val TAG = "ScanFragment"
        const val EXTRA_BARCODE_FORMAT = "$TAG.extra_barcode_format"
        const val EXTRA_BARCODE_TEXT = "$TAG.extra_barcode_text"

        private const val REQUEST_CAMERA = 16
    }

}
