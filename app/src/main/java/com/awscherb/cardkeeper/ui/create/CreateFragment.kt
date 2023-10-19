package com.awscherb.cardkeeper.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.ui.base.BaseFragment
import com.awscherb.cardkeeper.util.extensions.collapse
import com.awscherb.cardkeeper.util.extensions.expand
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CreateFragment : BaseFragment() {



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

        AndroidSupportInjection.inject(this)

        title = view.findViewById(R.id.fragment_create_title)
        text = view.findViewById(R.id.fragment_create_text)
        codeType = view.findViewById(R.id.fragment_create_code_type)
        createFab = view.findViewById(R.id.fragment_create_fab)
        typesRecyler = view.findViewById(R.id.fragment_create_types_recycler)
        bottomSheetLayout = view.findViewById(R.id.fragment_create_bottom_sheet)

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)

        setupRecycler()

        codeType.setOnClickListener {
            dismissKeyboard()
            bottomSheetBehavior.expand()
        }


    }

    //================================================================================
    // View methods
    //================================================================================


    private fun onSaveResult(result: SaveResult) {

    }

    private fun onSaveComplete(id: Int) {
        showSnackbar(R.string.fragment_create_complete)
    }

    //================================================================================
    // Helper methods
    //================================================================================

    private fun setupRecycler() {
        typesRecyler.layoutManager = LinearLayoutManager(requireContext())
        typesRecyler.adapter = CodeTypesAdapter(requireContext()) {
            codeType.text = it.title
        }
    }


}