package com.awscherb.cardkeeper.ui.card_detail

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.ui.base.BaseFragment
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife
import com.google.zxing.BarcodeFormat
import com.google.zxing.BarcodeFormat.*
import com.jakewharton.rxbinding2.widget.RxTextView

class CardDetailFragment : BaseFragment(), CardDetailContract.View {

    companion object {

        private const val TAG = "CardDetailFragment"
        private const val EXTRA_CARD_ID = "$TAG.extra_card_id"

        fun newInstance(cardId: Int): BaseFragment {
            val fragment = CardDetailFragment()
            val b = Bundle()

            b.putInt(EXTRA_CARD_ID, cardId)

            fragment.arguments = b
            return fragment
        }
    }

    @Inject internal lateinit var presenter: CardDetailContract.Presenter

    @BindView(R.id.fragment_card_detail_title) internal lateinit var title: TextView
    @BindView(R.id.fragment_card_detail_image) internal lateinit var imageView: ImageView
    @BindView(R.id.fragment_card_detail_text) internal lateinit var text: TextView
    @BindView(R.id.fragment_card_detail_save_fab) internal lateinit var fab: FloatingActionButton

    private val encoder: BarcodeEncoder = BarcodeEncoder()

    //================================================================================
    // Lifecycle methods
    //================================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        baseActivity.viewComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_card_detail, container, false)
        ButterKnife.bind(this, v)

        presenter.attachView(this)

        fab.setOnClickListener { presenter.saveCard() }

        return v
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
        title.text = code.title
        text.text = code.text

        activity!!.title = code.title

        // Set image scaleType according to barcode type
        val scaleType = when (code.format) {
            QR_CODE, AZTEC, DATA_MATRIX -> ImageView.ScaleType.FIT_CENTER
            else -> ImageView.ScaleType.FIT_XY
        }
        imageView.scaleType = scaleType

        // Load image
        try {
            imageView.setImageBitmap(
                    encoder.encodeBitmap(code.text, code.format, 200, 200))
        } catch (e: WriterException) {
            e.printStackTrace()
        }

        RxTextView.textChanges(title)
                .map { it.toString() }
                .compose(bindToLifecycle())
                .subscribe({ presenter.setTitle(it) },
                        { onError(it) })

    }

    override fun setSaveVisible(visible: Boolean) {
        fab.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun onCardSaved() {
        activity!!.finish()
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
    }

}
