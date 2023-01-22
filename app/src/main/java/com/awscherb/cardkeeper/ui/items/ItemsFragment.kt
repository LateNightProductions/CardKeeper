package com.awscherb.cardkeeper.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.model.PkPassModel
import com.awscherb.cardkeeper.data.model.ScannedCodeModel
import com.awscherb.cardkeeper.ui.base.BaseFragment
import com.awscherb.cardkeeper.ui.base.CardKeeperNavigator
import com.awscherb.cardkeeper.ui.card_detail.CardDetailViewModel
import com.awscherb.cardkeeper.ui.card_detail.CardDetailViewModelFactory
import com.awscherb.cardkeeper.ui.pkpassDetail.PkPassViewModel
import com.awscherb.cardkeeper.ui.pkpassDetail.PkPassViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ItemsFragment : BaseFragment() {

    private val viewModel by activityViewModels<ItemsViewModel> { viewModelFactory }
    private val detailViewModel by activityViewModels<CardDetailViewModel> { detailFactory }
    private val pkPassViewModel by activityViewModels<PkPassViewModel> { pkPassFactory }

    @Inject
    lateinit var viewModelFactory: ItemsViewModelFactory
    @Inject
    lateinit var detailFactory: CardDetailViewModelFactory
    @Inject
    lateinit var pkPassFactory: PkPassViewModelFactory

    private val navigator by lazy { CardKeeperNavigator(resources.getBoolean(R.bool.portrait)) }

    private lateinit var toolbar: Toolbar
    private lateinit var searchLayout: View
    private lateinit var searchBox: EditText
    private lateinit var searchClose: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var scannedCodeAdapter: ItemsAdapter

    //================================================================================
    // Lifecycle methods
    //================================================================================

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_items, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AndroidSupportInjection.inject(this)

        toolbar = view.findViewById(R.id.fragment_cards_toolbar)
        recyclerView = view.findViewById(R.id.fragment_cards_recylcer)
        searchLayout = view.findViewById(R.id.fragment_cards_search_layout)
        searchBox = view.findViewById(R.id.fragment_cards_search)
        searchClose = view.findViewById(R.id.fragment_cards_search_close)

        fab = view.findViewById(R.id.fragment_cards_fab)

        setupRecycler()
        setupToolbar()

        fab.setOnClickListener {
            findNavController().navigate(
                ItemsFragmentDirections.actionCardsFragmentToScanFragment()
            )
        }

        viewModel.items.onEach {
            scannedCodeAdapter.items = it
        }.launchIn(lifecycleScope)

        searchClose.setOnClickListener {
            dismissKeyboard()
            searchBox.setText("")
            searchLayout.visibility = View.GONE
            toolbar.visibility = View.VISIBLE
        }

        searchBox.addLifecycleTextWatcher {
            viewModel.searchQuery.value = it
        }
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
                R.id.search -> {
                    toolbar.visibility = View.GONE
                    searchBox.requestFocusAndShowKeyboard()
                    searchLayout.visibility = View.VISIBLE
                    true
                }
                else -> false
            }
        }
    }

    private fun setupRecycler() {
        layoutManager = GridLayoutManager(activity, resources.getInteger(R.integer.cards_columns))
        scannedCodeAdapter = ItemsAdapter(requireActivity(), {
            when (it) {
                is PkPassModel -> {
                    pkPassViewModel.pass.value = it
                    navigator.navigateToPkPass(this)
                }
                is ScannedCodeModel -> {
                    detailViewModel.cardId.value = it.id
                    navigator.navigateToDetail(this, it.id)
                }
            }
        }) { code ->
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.adapter_scanned_code_delete_message)
                .setPositiveButton(R.string.action_delete) { _, _ ->
                    when (code) {
                        is PkPassModel -> {

                        }
                        is ScannedCodeModel -> {
                            viewModel.deleteCard(code)
                            onCardDeleted()
                        }
                    }
                }
                .setNegativeButton(R.string.action_cancel, null)
                .show()
        }
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = scannedCodeAdapter
    }
}
