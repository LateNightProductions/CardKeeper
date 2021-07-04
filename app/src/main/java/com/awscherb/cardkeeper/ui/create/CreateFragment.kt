package com.awscherb.cardkeeper.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.ui.base.BaseFragment
import com.awscherb.cardkeeper.util.extensions.collapse
import com.awscherb.cardkeeper.util.extensions.expand
import com.awscherb.cardkeeper.util.extensions.textChanges
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CreateFragment : BaseFragment() {

    private val viewModel by viewModels<CreateViewModel> { viewModelFactory }

    @Inject
    lateinit var viewModelFactory: CreateViewModelFactory

    private lateinit var title: TextInputEditText
    private lateinit var text: TextInputEditText
    private lateinit var codeType: Button
    private lateinit var createFab: FloatingActionButton
    private lateinit var bottomSheetLayout: LinearLayout
    private lateinit var typesRecyler: RecyclerView
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

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

        title = view.findViewById(R.id.fragment_create_title)
        text = view.findViewById(R.id.fragment_create_text)
        codeType = view.findViewById(R.id.fragment_create_code_type)
        createFab = view.findViewById(R.id.fragment_create_fab)
        typesRecyler = view.findViewById(R.id.fragment_create_types_recycler)
        bottomSheetLayout = view.findViewById(R.id.fragment_create_bottom_sheet)

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)

        setupRecycler()
        setupTextListeners()

        codeType.setOnClickListener {
            dismissKeyboard()
            bottomSheetBehavior.expand()
        }

        viewModel.format.observe(viewLifecycleOwner, {
            if (it != null) {
                codeType.text = it.title
            }
        })
        viewModel.saveResult.observe(viewLifecycleOwner, {
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
        typesRecyler.layoutManager = LinearLayoutManager(requireContext())
        typesRecyler.adapter = CodeTypesAdapter(requireContext()) {
            codeType.text = it.title
            viewModel.format.postValue(it)
            bottomSheetBehavior.collapse()
        }
    }

    private fun setupTextListeners() {
        title.textChanges()
            .onEach { viewModel.title.postValue(it) }
            .launchIn(viewLifecycleOwner.lifecycle.coroutineScope)

        text.textChanges()
            .onEach { viewModel.text.postValue(it) }
            .launchIn(viewLifecycleOwner.lifecycle.coroutineScope)
    }

}