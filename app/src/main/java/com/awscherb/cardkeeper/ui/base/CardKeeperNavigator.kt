package com.awscherb.cardkeeper.ui.base

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.ui.items.ItemsFragmentDirections

class CardKeeperNavigator(
    private val portrait: Boolean
) {

    fun navigateToDetail(fragment: Fragment, id: Int) {
        if (portrait) {
            fragment.findNavController().navigate(
                ItemsFragmentDirections.actionCardsFragmentToCardDetailFragment(id)
            )
        } else {
            fragment.activity?.findNavController(R.id.nav_host_fragment)
                ?.navigate(R.id.action_global_cardDetailFragment)
        }
    }

}