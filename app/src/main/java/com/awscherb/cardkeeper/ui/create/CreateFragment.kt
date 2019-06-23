package com.awscherb.cardkeeper.ui.create

import android.os.Bundle
import android.os.Parcel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.ui.base.BaseFragment
import com.awscherb.cardkeeper.util.extensions.collapse
import com.awscherb.cardkeeper.util.extensions.expand
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.zxing.BarcodeFormat
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.fragment_create.*
import javax.inject.Inject

class CreateFragment : BaseFragment(), CreateContract.View {

    @Inject
    lateinit var presenter: CreateContract.Presenter

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
        presenter.attachView(this)

        bottomSheet = BottomSheetBehavior.from(createBottomSheet)

        setupRecycler()
        setupTextListeners()

        createCodeType.setOnClickListener {
            dismissKeyboard()
            bottomSheet.expand()
        }

        savedInstanceState?.let {
            presenter.restoreState(it)
        }

        createFab.setOnClickListener { presenter.save() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.saveState(outState)
    }

    //================================================================================
    // View methods
    //================================================================================

    override fun onStateRestored(title: String?, text: String?, format: BarcodeFormat?) {
        title?.let {
            createTitle.setText(it)
        }

        text?.let {
            createText.setText(it)
        }

        format?.let {
            val type = CreateType.typeForFormat(it)
            createCodeType.text = type.title
        }
    }

    override fun onSaveError(error: SaveError) {
        showSnackbar(
            when (error) {
                SaveError.InvalidTitle -> R.string.fragment_create_invalid_title
                SaveError.InvalidText -> R.string.fragment_create_invalid_text
                SaveError.InvalidFormat -> R.string.fragment_create_invalid_format
            }
        )
    }

    override fun onSaveComplete(id: Int) {
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
            presenter.setFormat(it.format)
            bottomSheet.collapse()
        }
    }

    private fun setupTextListeners() {
        addDisposable(
            createTitle.textChanges()
                .map { it.toString() }
                .subscribe(presenter::setTitle)
        )

        addDisposable(createText.textChanges()
            .map { it.toString() }
            .subscribe(presenter::setText))
    }

}