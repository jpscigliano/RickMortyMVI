package com.testme.feedpresentation


import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.testme.feedpresentation.characterList.mapper.CharacterToCharacterUiModel
import com.testme.feedpresentation.characterList.reducer.CharactersReducer
import com.testme.feedpresentation.characterList.view.CharactersFeedFragment
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@HiltAndroidTest
@RunWith(AndroidJUnit4ClassRunner::class)
class FeedInstrumentedTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @BindValue
    val charactersReducer: CharactersReducer = CharactersReducer(CharacterToCharacterUiModel())

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun Given_charactersAreLoadedOnCharactecterListFragment_When_userClicksOnCharacter_Then_CharacterDetailScreenIsShownWithCharacter() {

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val childNavController = TestNavHostController(ApplicationProvider.getApplicationContext())

        launchFragmentInHiltContainer<CharactersFeedFragment> {
            navController.setGraph(R.navigation.nav_feed)
            Navigation.setViewNavController(this.requireView(), navController)

        }

        val navHostFragment = childFragmentManager.findFragmentById(R.id.detail_container) as NavHostFragment
        val navController = navHostFragment.navController

        onView(withId(R.id.rv_characters)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                click()
            )
        )
        Assert.assertEquals(navController.currentDestination?.id, R.id.fragmentCharacterDetail)

    }

    fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints() = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }

}