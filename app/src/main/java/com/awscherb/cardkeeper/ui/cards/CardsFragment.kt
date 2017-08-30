package com.awscherb.cardkeeper.ui.cards

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.ui.base.BaseFragment
import com.awscherb.cardkeeper.ui.card_detail.CardDetailActivity
import com.awscherb.cardkeeper.ui.listener.RecyclerItemClickListener
import com.awscherb.cardkeeper.ui.scan.ScanActivity
import com.awscherb.cardkeeper.ui.scan.ScanFragment
import com.google.zxing.BarcodeFormat

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife

import android.app.Activity.RESULT_OK
import android.support.v4.app.FragmentActivity

class CardsFragment : BaseFragment(), CardsContract.View {

    companion object {

        fun newInstance() = CardsFragment()
    }

    @Inject internal lateinit var presenter: CardsContract.Presenter

    @BindView(R.id.fragment_cards_recycler) internal lateinit var recyclerView: RecyclerView

    internal lateinit var layoutManager: LinearLayoutManager
    internal lateinit var scannedCodeAdapter: CardsAdapter

    //================================================================================
    // Lifecycle methods
    //================================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        baseActivity.viewComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_cards, container, false)
        ButterKnife.bind(this, v)

        layoutManager = LinearLayoutManager(activity)
        scannedCodeAdapter = CardsAdapter(activity, presenter)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = scannedCodeAdapter

        setupListeners()

        presenter.attachView(this)

        return v
    }

    override fun onResume() {
        super.onResume()
        presenter.loadCards()
    }

    override fun onPause() {
        presenter.onViewDestroyed()
        super.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CardsActivity.REQUEST_GET_CODE && resultCode == RESULT_OK) {
            val scannedCode = ScannedCode()
            scannedCode.text = data!!.getStringExtra(ScanFragment.EXTRA_BARCODE_TEXT)
            scannedCode.format = data.getSerializableExtra(ScanFragment.EXTRA_BARCODE_FORMAT) as BarcodeFormat

            val input = EditText(activity)
            input.setHint(R.string.dialog_card_name_hint)
            input.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS

            AlertDialog.Builder(activity)
                    .setTitle(R.string.app_name)
                    .setView(input)
                    .setPositiveButton(R.string.action_add) { _, _ ->
                        scannedCode.title = input.text.toString()
                        presenter.addNewCard(scannedCode)
                    }
                    .setNegativeButton(R.string.action_cancel) { dialog, _ -> dialog.dismiss() }
                    .show()
        }
    }

    //================================================================================
    // View methods
    //================================================================================

    override fun showCards(codes: List<ScannedCode>) {
        scannedCodeAdapter.swapObjects(codes)
    }

    override fun onCardAdded(code: ScannedCode) {
        Snackbar.make(view!!, getString(R.string.fragment_cards_added_card, code.title), Snackbar.LENGTH_SHORT).show()
    }

    override fun onCardDeleted() {
        Snackbar.make(view!!, R.string.fragment_cards_deleted_card, Snackbar.LENGTH_SHORT).show()
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
    }

    //================================================================================
    // Helper methods
    //================================================================================

    private fun setupListeners() {
        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(activity,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val i = Intent(activity, CardDetailActivity::class.java)
                        i.putExtra(CardDetailActivity.EXTRA_CARD_ID,
                                scannedCodeAdapter.getItem(position).id)
                        startActivity(i)
                    }

                }))
    }

}
