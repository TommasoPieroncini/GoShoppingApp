package com.pieroncini.tommaso.goshopping;

import org.junit.Test;

import static org.junit.Assert.*;
import com.shauvik.calc.CalcActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static org.hamcrest.Matchers.*;
import android.test.suitebuilder.annotation.LargeTest;
import static com.checkdroid.crema.EspressoPlus.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        onView(withXPath("/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.RelativeLayout")).perform(click());
        onView(withId(R.id.button2)).perform(click());
        onView(withId(R.id.editText11)).perform(clearText(), typeText("kg"));
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withId(R.id.action_refresh)).perform(click());
        onView(withId(R.id.button2)).perform(click());
        onView(withId(R.id.editText12)).perform(clearText(), typeText("CG"));
        onView(withId(R.id.editText9)).perform(clearText(), typeText("o"));
        onView(withId(R.id.editText12)).perform(click());
        onView(withId(R.id.editText12)).perform(clearText(), typeText("CGte"));
    }
}