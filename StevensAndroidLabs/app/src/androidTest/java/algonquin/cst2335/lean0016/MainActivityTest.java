package algonquin.cst2335.lean0016;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.core.widget.TextViewCompat;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.ViewInteractionModule_ProvideNeedsActivityFactory;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    public void mainActivityTest() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(withId(R.id.editText));
        appCompatEditText2.perform(pressImeActionButton());

        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    /**
     * This function is for testing the code
     * Test the if the password miss uppercase letter
     */
    @Test
    public void testFindMissingUpperCase(){
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText));
        //type in password123#$*
        appCompatEditText.perform(replaceText("password123#$*"));

        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView(withId(R.id.textView));
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }
    /**
     * This function is for testing the code
     * Test the if the password miss lowercase letter
     */
    @Test
    public void testFindMissingLowerCase(){
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText));
        //type in password123#$*
        appCompatEditText.perform(replaceText("123#$*"));

        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView(withId(R.id.textView));
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }
    /**
     * This function is for testing the code
     * Test the if the password miss numbers
     */
    @Test
    public void testFindMissingNumber(){
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText));
        //type in password123#$*
        appCompatEditText.perform(replaceText("password#$*"));

        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView(withId(R.id.textView));
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }
    /**
     * This function is for testing the code
     * Test the if the password miss special characters
     */
    @Test
    public void testFindMissingSpecial(){
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText));
        //type in password123#$*
        appCompatEditText.perform(replaceText("password123"));

        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView(withId(R.id.textView));
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }
    /**
     * This function is for testing the code
     * Test the if the password meet all the requirements
     */
    @Test
    public void testMeetRequirement(){
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText));
        //type in password123#$*
        appCompatEditText.perform(replaceText("Password123#$*"));
        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView(withId(R.id.textView));
        //check the text
        textView.check(matches(withText("Your password meet the requirements!")));

    }
}
