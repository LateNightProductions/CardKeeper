package com.awscherb.cardkeeper.ui.cards

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.ui.base.BaseFragment
import com.awscherb.cardkeeper.ui.card_detail.CardDetailActivity
import com.awscherb.cardkeeper.ui.listener.RecyclerItemClickListener
import com.awscherb.cardkeeper.ui.scan.ScanFragment
import com.google.zxing.BarcodeFormat
import kotlinx.android.synthetic.main.fragment_cards.*
import javax.inject.Inject

class CardsFragment : BaseFragment(), CardsContract.View {

    @Inject
    internal lateinit var presenter: CardsContract.Presenter

    private lateinit var layoutManager: androidx.recyclerview.widget.LinearLayoutManager
    private lateinit var scannedCodeAdapter: CardsAdapter

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
    ): View = inflater.inflate(R.layout.fragment_cards, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        setupListeners()

        presenter.attachView(this)

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
            scannedCode.format =
                    data.getSerializableExtra(ScanFragment.EXTRA_BARCODE_FORMAT) as BarcodeFormat

            val input = EditText(activity).apply {
                setHint(R.string.dialog_card_name_hint)
                inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
            }

            AlertDialog.Builder(activity!!)
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
        showSnackbar(getString(R.string.fragment_cards_added_card, code.title))
    }

    override fun onCardDeleted() {
        showSnackbar(R.string.fragment_cards_deleted_card)
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
    }

    //================================================================================
    // Helper methods
    //================================================================================

    private fun setupRecycler() {
        layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        scannedCodeAdapter = CardsAdapter(activity!!) { code ->
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.adapter_scanned_code_delete_message)
                .setPositiveButton(R.string.action_delete) { _, _ ->
                    presenter.deleteCard(code)
                }
                .setNegativeButton(R.string.action_cancel, null)
                .show()
        }
        cardsRecycler.layoutManager = layoutManager
        cardsRecycler.adapter = scannedCodeAdapter
    }

    private fun setupListeners() {
        cardsRecycler.addOnItemTouchListener(
            RecyclerItemClickListener(activity!!,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        startActivity(
                            Intent(activity, CardDetailActivity::class.java)
                                .apply {
                                    putExtra(
                                        CardDetailActivity.EXTRA_CARD_ID,
                                        scannedCodeAdapter[position].id
                                    )
                                }
                        )
                    }

                }
            )
        )
    }

    companion object {
        fun newInstance() = CardsFragment()
    }
}
