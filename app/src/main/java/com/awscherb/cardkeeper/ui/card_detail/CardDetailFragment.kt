package com.awscherb.cardkeeper.ui.card_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.entity.ScannedCodeEntity
import com.awscherb.cardkeeper.ui.base.BaseFragment
import com.awscherb.cardkeeper.ui.create.Failure
import com.awscherb.cardkeeper.ui.create.InvalidFormat
import com.awscherb.cardkeeper.ui.create.InvalidText
import com.awscherb.cardkeeper.ui.create.InvalidTitle
import com.awscherb.cardkeeper.ui.create.SaveResult
import com.awscherb.cardkeeper.ui.create.SaveSuccess
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.zxing.BarcodeFormat.AZTEC
import com.google.zxing.BarcodeFormat.DATA_MATRIX
import com.google.zxing.BarcodeFormat.QR_CODE
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CardDetailFragment : BaseFragment() {

    private val viewModel by activityViewModels<CardDetailViewModel> {
        factory
    }

    @Inject
    lateinit var factory: CardDetailViewModelFactory

    private val encoder: BarcodeEncoder = BarcodeEncoder()

    private lateinit var toolbar: Toolbar
    private lateinit var title: EditText
    private lateinit var text: TextView
    private lateinit var image: ImageView
    private lateinit var save: FloatingActionButton

    //================================================================================
    // Lifecycle methods
    //================================================================================

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_card_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)

        toolbar = view.findViewById(R.id.fragment_card_detail_toolbar)
        title = view.findViewById(R.id.fragment_card_detail_title)
        text = view.findViewById(R.id.fragment_card_detail_text)
        image = view.findViewById(R.id.fragment_card_detail_image)
        save = view.findViewById(R.id.fragment_card_detail_save)

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

        save.setOnClickListener { viewModel.save() }

        viewModel.card.onEach {
            showCard(it)
        }.launchIn(lifecycleScope)

        viewModel.saveResult
            .filterNotNull()
            .onEach {
                onSaveResult(it)
                viewModel.saveResult.value = null
            }
            .launchIn(lifecycleScope)
    }

    //================================================================================
    // View methods
    //================================================================================

    private fun showCard(code: ScannedCodeEntity) {
        // Set title
        title.setText(code.title)
        text.setText(code.text)

        toolbar.title = code.title

        // Set image scaleType according to barcode type
        val scaleType = when (code.format) {
            QR_CODE, AZTEC, DATA_MATRIX -> ImageView.ScaleType.FIT_CENTER
            else -> ImageView.ScaleType.FIT_XY
        }
        image.scaleType = scaleType

        // Load image
        try {
            image.setImageBitmap(
                encoder.encodeBitmap(code.text, code.format, 200, 200)
            )
        } catch (e: WriterException) {
            e.printStackTrace()
        }

        title.addLifecycleTextWatcher {
            viewModel.title.value = it
        }
    }

    private fun onSaveResult(result: SaveResult) {
        when (result) {
            InvalidTitle -> showSnackbar(R.string.fragment_create_invalid_title)
            InvalidText -> showSnackbar(R.string.fragment_create_invalid_text)
            InvalidFormat -> showSnackbar(R.string.fragment_create_invalid_format)
            is Failure -> showSnackbar(
                result.e.message ?: getString(R.string.fragment_create_error_generic)
            )

            is SaveSuccess -> onCardSaved()
        }
    }


    private fun setSaveVisible(visible: Boolean) {
        save.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun onCardSaved() {
        showSnackbar(R.string.fragment_card_detail_saved)
        activity?.onBackPressed()
    }

}
