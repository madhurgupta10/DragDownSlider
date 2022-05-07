package com.github.madhurgupta10.dragdownslider.sampleApp

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.madhurgupta10.dragdownslider.sampleApp.ui.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalMaterialApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_error_case_view_is_correctly_displayed() {
        composeTestRule.onNodeWithTag("toggle").assertIsDisplayed()
        composeTestRule.onNodeWithText("Error Case").assertIsDisplayed()
        composeTestRule.onNodeWithTag("dragdownslider").assertIsDisplayed()
        composeTestRule.onNodeWithTag("parentCard").assertIsDisplayed()
        composeTestRule.onNodeWithTag("dragTarget").assertIsDisplayed()
        composeTestRule.onNodeWithTag("draggable").assertIsDisplayed()
    }

    @Test
    fun check_success_case_view_is_correctly_displayed() {
        composeTestRule.onNodeWithTag("toggle").assertIsDisplayed().performClick()
        composeTestRule.onNodeWithText("Success Case").assertIsDisplayed()
        composeTestRule.onNodeWithTag("dragdownslider").assertIsDisplayed()
        composeTestRule.onNodeWithTag("parentCard").assertIsDisplayed()
        composeTestRule.onNodeWithTag("dragTarget").assertIsDisplayed()
        composeTestRule.onNodeWithTag("draggable").assertIsDisplayed()
    }

}