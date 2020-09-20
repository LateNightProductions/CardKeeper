package com.awscherb.cardkeeper.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.ui.base.BaseFragment
import com.awscherb.cardkeeper.util.extensions.collapse
import com.awscherb.cardkeeper.util.extensions.expand
import com.awscherb.cardkeeper.util.extensions.textChanges
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.fragment_create.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CreateFragment : BaseFragment() {

    private val viewModel by viewModels<CreateViewModel> { viewModelFactory }

    @Inject
    lateinit var viewModelFactory: CreateViewModelFactory

    private lateinit var bottomSheet: BottomSheetBehavior<LinearLayout>

    //================================================================================
    // Lifecycle methods
    //================================================================================

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_create, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewComponent.inject(this)

        bottomSheet = BottomSheetBehavior.from(createBottomSheet)

        setupRecycler()
        setupTextListeners()

        createCodeType.setOnClickListener {
            dismissKeyboard()
            bottomSheet.expand()
        }

        viewModel.format.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                createCodeType.text = it.title
            }
        })
        viewModel.saveResult.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.onSaveResult(it)
            }
        })

        createFab.setOnClickListener { viewModel.save() }
    }

    //================================================================================
    // View methods
    //================================================================================


    private fun onSaveResult(result: SaveResult) {
        when (result) {
            InvalidTitle -> showSnackbar(R.string.fragment_create_invalid_title)
            InvalidText -> showSnackbar(R.string.fragment_create_invalid_text)
            InvalidFormat -> showSnackbar(R.string.fragment_create_invalid_format)
            is SaveSuccess -> onSaveComplete(result.codeId)
        }
        viewModel.saveResult.postValue(null)
    }

    private fun onSaveComplete(id: Int) {
        showSnackbar(R.string.fragment_create_complete)
        findNavController().navigate(
            CreateFragmentDirections.actionCreateFragmentToCardDetailFragment(id)
        )
    }

    //================================================================================
    // Helper methods
    //================================================================================

    private fun setupRecycler() {
        createTypesRecycler.layoutManager = LinearLayoutManager(requireContext())
        createTypesRecycler.adapter = CodeTypesAdapter(requireContext()) {
            createCodeType.text = it.title
            viewModel.format.postValue(it)
            bottomSheet.collapse()
        }
    }

    private fun setupTextListeners() {
        createTitle.textChanges()
            .onEach { viewModel.title.postValue(it) }
            .launchIn(viewLifecycleOwner.lifecycle.coroutineScope)

        createText.textChanges()
            .onEach { viewModel.text.postValue(it) }
            .launchIn(viewLifecycleOwner.lifecycle.coroutineScope)
    }

}