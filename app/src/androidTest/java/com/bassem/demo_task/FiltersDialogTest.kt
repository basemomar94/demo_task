import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.runner.AndroidJUnit4
import com.bassem.demo_task.MainActivity
import com.bassem.demo_task.R
import com.bassem.demo_task.utils.AppConstants
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FiltersDialogTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testFilterDialogLaunchesCorrectly() {
        openFiltersDialog()
        onView(withId(R.id.filter_container)).check(matches(isDisplayed()))
    }

    @Test
    fun testFilterDialogDisplaysCorrectly() {
        openFiltersDialog()
        val filters = listOf(
            AppConstants.ALL,
            AppConstants.FINISHED,
            AppConstants.MATCH_IN_PROGRESS,
            AppConstants.SCHEDULED,
            AppConstants.HALF_TIME,
            AppConstants.FULL_TIME,
            AppConstants.EXTRA_TIME,
            AppConstants.PENALTIES
        )
        filters.forEach { filter ->
            onView(withText(filter)).check(matches(isDisplayed()))
        }

    }

    @Test
    fun testFilterSelectionDismissDialog() {
        openFiltersDialog()
        val selectedFilter = AppConstants.FINISHED
        onView(withText(selectedFilter)).perform(click())
        onView(withId(R.id.filter_container)).check(doesNotExist())
    }

    private fun openFiltersDialog() {
        onView(withId(R.id.status_chip)).perform(click())
    }
}
