package com.akhutornoy.shoppinglist;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.akhutornoy.shoppinglist.ui.shops.manageshops.ManageShopsActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.akhutornoy.shoppinglist.ui.util.TestUtils.actionOnItemViewAtPosition;
import static com.akhutornoy.shoppinglist.ui.util.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ManageShopsActivityTest {

    private static final String TEST_SHOP_NAME = "Test Shop";

    @Rule
    public ActivityTestRule<ManageShopsActivity> mActivityTestRule = new ActivityTestRule<>(ManageShopsActivity.class);

    @Test
    public void toolbarIconAddShopsShowsDialog() {
                /*Click 'Add' icon*/
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.menu_add_or_edit), withContentDescription("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        /*inspect New Shop dialog*/
        onView(
                withText(getStringByResId(R.string.title_new_shop_name)))
                .check(matches(isDisplayed()));

        onView(withId(android.R.id.button1))
                .check(matches(isDisplayed()));
        onView(withId(android.R.id.button1))
                .check(matches(withText(android.R.string.ok)));

        /*close dialog*/
        onView(withId(android.R.id.button1)).perform(click());
    }

    @Test
    public void createShopTest () {
                /*Click 'Add' icon*/
        ViewInteraction addIcon = onView(
                allOf(withId(R.id.menu_add_or_edit), withContentDescription("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        addIcon.perform(click());

        /*enter shop's name*/
        onView(withId(R.id.edit_text)).perform(typeText(TEST_SHOP_NAME));
        /*save shop*/
        onView(withId(android.R.id.button1)).perform(click());

        onView(withRecyclerView(R.id.recycler_view)
                .atPositionOnView(0, R.id.tv_name))
                .check(matches(withText(TEST_SHOP_NAME)));
    }

    @Test
    public void removeTestShop() {
                        /*Click 'Add' icon*/
        ViewInteraction removeIcon = onView(
                allOf(withId(R.id.menu_delete), withContentDescription("Delete"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                1),
                        isDisplayed()));
        removeIcon.perform(click());

        onView(withId(R.id.recycler_view)).perform(actionOnItemViewAtPosition(0, R.id.iv_delete, click()));
    }

    @Test
    public void toolbarIconsExists() {

        /*if Add/Delete/Resort icons exists on Toolbar*/
        ViewInteraction toolbarIconAdd = onView(
                allOf(withId(R.id.menu_add_or_edit), withContentDescription("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        toolbarIconAdd.check(matches(isDisplayed()));

        ViewInteraction toolbarIconDelete = onView(
                allOf(withId(R.id.menu_delete), withContentDescription("Delete"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                1),
                        isDisplayed()));
        toolbarIconDelete.check(matches(withContentDescription(getStringByResId(R.string.delete))));
        toolbarIconDelete.check(matches(isDisplayed()));

        ViewInteraction toolbarIconResort = onView(
                allOf(withId(R.id.menu_resort), withContentDescription("Resort"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                2),
                        isDisplayed()));
        toolbarIconResort.check(matches(isDisplayed()));

        ViewInteraction button = onView(
                allOf(withId(R.id.bt_done),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                1),
                        isDisplayed()));
        button.check(matches(isDisplayed()));
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

    private String getStringByResId(int id) {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        return targetContext.getResources().getString(id);
    }
}
