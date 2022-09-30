package com.awscherb.cardkeeper.ui.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.ui.base.BaseFragment
import com.awscherb.cardkeeper.ui.base.CardKeeperNavigator
import com.awscherb.cardkeeper.ui.card_detail.CardDetailViewModel
import com.awscherb.cardkeeper.ui.card_detail.CardDetailViewModelFactory
import com.awscherb.cardkeeper.ui.screens.CardList
import javax.inject.Inject

class CardsFragmentCompose : BaseFragment() {


    private val viewModel by activityViewModels<CardsViewModel> { viewModelFactory }

    @Inject
    lateinit var viewModelFactory: CardsViewModelFactory

    private val detailViewModel by activityViewModels<CardDetailViewModel> { detailFactory }

    @Inject
    lateinit var detailFactory: CardDetailViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        viewComponent.inject(this@CardsFragmentCompose)
        setContent {
            CardList(
                findNavController(),
                viewModel,
                detailViewModel
            )
        }
    }
}