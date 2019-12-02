package com.awscherb.cardkeeper.ui.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_cards.*
import javax.inject.Inject

class CardsFragment : BaseFragment() {

    private val viewModel by activityViewModels<CardsViewModel> { viewModelFactory }

    @Inject
    lateinit var viewModelFactory: CardsViewModelFactory

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

        viewModel.cards.observe(viewLifecycleOwner, Observer(scannedCodeAdapter::swapObjects))
    }

    //================================================================================
    // View methods
    //================================================================================

    private fun onCardDeleted() {
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
                    viewModel.deleteCard(code)
                    onCardDeleted()
                }
                .setNegativeButton(R.string.action_cancel, null)
                .show()
        }
        cardsRecycler.layoutManager = layoutManager
        cardsRecycler.adapter = scannedCodeAdapter
    }
}
