package com.awscherb.cardkeeper.ui.card_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.ui.base.BaseFragment
import com.google.zxing.BarcodeFormat.*
import com.google.zxing.WriterException
import com.jakewharton.rxbinding3.widget.textChanges
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.fragment_card_detail.*
import javax.inject.Inject

class CardDetailFragment : BaseFragment(), CardDetailContract.View {

    @Inject
    internal lateinit var presenter: CardDetailContract.Presenter

    private val encoder: BarcodeEncoder = BarcodeEncoder()

    //================================================================================
    // Lifecycle methods
    //================================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        baseActivity.viewComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_card_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        cardDetailSave.setOnClickListener { presenter.saveCard() }
    }

    override fun onResume() {
        super.onResume()
        presenter.loadCard(arguments!!.getInt(EXTRA_CARD_ID))
    }

    override fun onDestroy() {
        presenter.onViewDestroyed()
        super.onDestroy()
    }

    //================================================================================
    // View methods
    //================================================================================

    override fun showCard(code: ScannedCode) {
        // Set title
        cardDetailTitle.setText(code.title)
        cardDetailText.text = code.text

        activity!!.title = code.title

        // Set image scaleType according to barcode type
        val scaleType = when (code.format) {
            QR_CODE, AZTEC, DATA_MATRIX -> ImageView.ScaleType.FIT_CENTER
            else -> ImageView.ScaleType.FIT_XY
        }
        cardDetailImage.scaleType = scaleType

        // Load image
        try {
            cardDetailImage.setImageBitmap(
                encoder.encodeBitmap(code.text, code.format, 200, 200)
            )
        } catch (e: WriterException) {
            e.printStackTrace()
        }

        addDisposable(
            cardDetailTitle.textChanges()
                .map { it.toString() }
                .compose(bindToLifecycle())
                .subscribe({ presenter.setTitle(it) },
                    { onError(it) })
        )

    }

    override fun setSaveVisible(visible: Boolean) {
        cardDetailSave.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun onCardSaved() {
        activity!!.finish()
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
    }

    companion object {

        private const val TAG = "CardDetailFragment"
        private const val EXTRA_CARD_ID = "$TAG.extra_card_id"

        fun newInstance(cardId: Int) = CardDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(EXTRA_CARD_ID, cardId)
            }
        }
    }


}
