package org.djodjo.tarator.testapp.test;

import static org.djodjo.tarator.Tarator.onData;
import static org.djodjo.tarator.Tarator.onView;
import static org.djodjo.tarator.action.ViewActions.clearText;
import static org.djodjo.tarator.action.ViewActions.click;
import static org.djodjo.tarator.action.ViewActions.scrollTo;
import static org.djodjo.tarator.action.ViewActions.typeText;
import static org.djodjo.tarator.action.ViewActions.typeTextIntoFocusedView;
import static org.djodjo.tarator.assertion.ViewAssertions.matches;
import static org.djodjo.tarator.matcher.RootMatchers.withDecorView;
import static org.djodjo.tarator.matcher.ViewMatchers.withId;
import static org.djodjo.tarator.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.djodjo.tarator.testapp.R;
import org.djodjo.tarator.testapp.SendActivity;

import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

/**
 * Demonstrates dealing with multiple windows.
 *
 * Tarator provides the ability to switch the default window matcher used in both onView and onData
 * interactions.
 *
 * @see org.djodjo.tarator.Tarator#onView
 * @see org.djodjo.tarator.Tarator#onData
 */
@LargeTest
public class MultipleWindowTest extends ActivityInstrumentationTestCase2<SendActivity> {

  @SuppressWarnings("deprecation")
  public MultipleWindowTest() {
    // This constructor was deprecated - but we want to support lower API levels.
    super("org.djodjo.tarator.testapp", SendActivity.class);
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    // Tarator will not launch our activity for us, we must launch it via getActivity().
    getActivity();
  }

  public void testInteractionsWithAutoCompletePopup() {
    if (Build.VERSION.SDK_INT < 10) {
      // Froyo's AutoCompleteTextBox is broken - do not bother testing with it.
      return;
    }
    // Android's Window system allows multiple view hierarchies to layer on top of each other.
    //
    // A real world analogy would be an overhead projector with multiple transparencies placed
    // on top of each other. Each Window is a transparency, and what is drawn on top of this
    // transparency is the view hierarchy.
    //
    // By default Tarator uses a heuristic to guess which Window you intend to interact with.
    // This heuristic is normally 'good enough' however if you want to interact with a Window
    // that it does not select then you'll have to swap in your own root window matcher.


    // Initially we only have 1 window, but by typing into the auto complete text view another
    // window will be layered on top of the screen. Tarator ignore's this layer because it is
    // not connected to the keyboard/ime.
    onView(withId(R.id.auto_complete_text_view))
        .perform(scrollTo())
        .perform(typeText("So"));

    // As you can see, we continue typing oblivious to the new window on the screen.
    // At the moment there should be 2 completions (South China Sea and Southern Ocean)
    // Lets narrow that down to 1 completion.
    onView(withId(R.id.auto_complete_text_view))
        .perform(typeTextIntoFocusedView("uth "));

    // Now we may want to explicitly tap on a completion. We must override Tarator's
    // default window selection heuristic with our own.
    onView(withText("South China Sea"))
        .inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView()))))
        .perform(click());

    // And by clicking on the auto complete term, the text should be filled in.
    onView(withId(R.id.auto_complete_text_view))
        .check(matches(withText("South China Sea")));


    // NB: The autocompletion box is implemented with a ListView, so the preferred way
    // to interact with it is onData(). We can use inRoot here too!
    onView(withId(R.id.auto_complete_text_view))
        .perform(clearText())
        .perform(typeText("S"));

    // Which is useful because some of the completions may not be part of the View Hierarchy
    // unless you scroll around the list.
    onData(allOf(instanceOf(String.class), is("Baltic Sea")))
        .inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView()))))
        .perform(click());

    onView(withId(R.id.auto_complete_text_view))
        .check(matches(withText("Baltic Sea")));
  }

}


