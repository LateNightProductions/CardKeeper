package com.awscherb.cardkeeper.ui.card_detail

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.entity.ScannedCodeEntity
import com.awscherb.cardkeeper.di.module.MockScannedCodeService
import com.google.zxing.BarcodeFormat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class CardDetailFragmentTest {

    @Rule
    @JvmField
    val testRule: ActivityTestRule<CardDetailActivity> =
            ActivityTestRule<CardDetailActivity>(CardDetailActivity::class.java, true, false)

    @Before
    fun setup() {
        MockScannedCodeService.codes[1] = ScannedCodeEntity().apply {
            title = "Title"
            text = "Text"
            format = BarcodeFormat.EAN_13
        }
    }

    @Test
    fun testSomething() {
        testRule.launchActivity(Intent().apply { putExtra(CardDetailActivity.EXTRA_CARD_ID, 1) })

        onView(withId(R.id.fragment_card_detail_text))
                .check(matches(withText("Text")));
    }

}