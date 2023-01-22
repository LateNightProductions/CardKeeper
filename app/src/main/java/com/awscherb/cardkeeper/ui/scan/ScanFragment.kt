package com.awscherb.cardkeeper.ui.scan

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.ui.base.BaseFragment
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView
import dagger.android.support.AndroidSupportInjection
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class ScanFragment : BaseFragment() {

    private val viewModel by viewModels<ScanViewModel> { factory }

    @Inject
    lateinit var factory: ScanViewModelFactory

    private lateinit var scannerView: CompoundBarcodeView

    private val found = AtomicBoolean(false)

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

        AndroidSupportInjection.inject(this)

        scannerView = view.findViewById(R.id.fragment_scan_scanner)

        // Check permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity?.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                scannerView.resume()
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CAMERA
                )
            }
        }

        // Setup scanner
        setCallback()

        viewModel.createResult.observe(viewLifecycleOwner) {
            onCodeAdded()
        }
    }

    override fun onResume() {
        super.onResume()
        scannerView.resume()
    }

    override fun onPause() {
        super.onPause()
        scannerView.pause()
    }

    private fun onCodeAdded() {
        findNavController().navigate(
            ScanFragmentDirections.actionScanFragmentToCardsFragment()
        )
    }

    // ========================================================================
    // Helper methods
    // ========================================================================

    private fun setCallback() {
        val callback = object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult) {
                if (found.compareAndSet(false, true)) {
                    val input = EditText(activity).apply {
                        setHint(R.string.dialog_card_name_hint)
                        inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
                    }

                    AlertDialog.Builder(activity!!)
                        .setTitle(R.string.app_name)
                        .setView(input)
                        .setOnDismissListener { found.set(false) }
                        .setPositiveButton(R.string.action_add) { _, _ ->
                            viewModel.createData.postValue(
                                CreateCodeData(
                                    format = result.barcodeFormat,
                                    text = result.text,
                                    title = input.text.toString()
                                )
                            )
                        }
                        .setNegativeButton(R.string.action_cancel) { dialog, _ -> dialog.dismiss() }
                        .show()
                }
            }

            override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
        }

        scannerView.decodeContinuous(callback)
    }

    companion object {
        private const val REQUEST_CAMERA = 16
    }
}
