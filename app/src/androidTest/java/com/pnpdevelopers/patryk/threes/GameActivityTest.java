package com.pnpdevelopers.patryk.threes;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.pnpdevelopers.patryk.threes.Activities.GameActivity;
import com.pnpdevelopers.patryk.threes.function.GameConditions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by pszen on 23.03.2018.
 */
@RunWith(AndroidJUnit4.class)
public class GameActivityTest {


    @Rule
    public ActivityTestRule<GameActivity> mainActivityActivityTestRule =
            new ActivityTestRule<GameActivity>(GameActivity.class);



    @Test
    public void clickOnConstraintLayout () throws InterruptedException {
        for(int i =0; i < 1200;i++) {
            if(GameConditions.successCondition(GameActivity.getNumber())){
                Log.d("click", String.valueOf(GameActivity.getNumber()));
                onView(withId(R.id.mainActivityLayoutID)).perform(click());
            }else{
                Log.d("swipe", String.valueOf(GameActivity.getNumber()));
                onView(withId(R.id.mainActivityLayoutID)).perform(swipeRight());
            }

        }

    }
}
