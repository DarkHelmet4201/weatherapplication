package test.weatherapplication.utilities

import android.app.Activity
import android.view.inputmethod.InputMethodManager

/**
 * Created by MyLaptop on 5/16/18.
 */


public class Util
{
    companion object {
        fun hideSoftKeyboard(activity : Activity)
        {
            val inputMethodManager : InputMethodManager = activity.getSystemService((Activity.INPUT_METHOD_SERVICE)) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus.windowToken, 0)
        }
    }

}