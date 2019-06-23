package com.awscherb.cardkeeper.ui.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_cards.*
import javax.inject.Inject

class CardsFragment : BaseFragment(), CardsContract.View {

    @Inject
    internal lateinit var presenter: CardsContract.Presenter

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var scannedCodeAdapter: CardsAdapter

    //================================================================================
    // Lifecycle methods
    //================================================================================

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_cards, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewComponent.inject(this)

        setupRecycler()
        setupToolbar()

        cardsFab.setOnClickListener {
            findNavController().navigate(
                CardsFragmentDirections.actionCardsFragmentToScanFragment()
            )
        }

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

    //================================================================================
    // Helper methods
    //================================================================================

    private fun setupToolbar() {
        toolbar.inflateMenu(R.menu.menu_cards)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.create -> {
                    findNavController().navigate(R.id.action_cardsFragment_to_createFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun setupRecycler() {
        layoutManager = GridLayoutManager(activity, resources.getInteger(R.integer.cards_columns))
        scannedCodeAdapter = CardsAdapter(activity!!, {
            findNavController().navigate(
                CardsFragmentDirections.actionCardsFragmentToCardDetailFragment(it.id)
            )
        }) { code ->
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
}
