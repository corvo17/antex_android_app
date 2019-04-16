package uz.codic.ahmadtea.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonUtils {

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getCurrentTime(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss, dd-MM-yyyy");
        return dateFormat.format(date);
    }

    public static String getToday(){
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        return df.format("yyyy-MM-dd", new java.util.Date()).toString();
    }

    public static long getCurrentTimeMilliseconds(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return date.getTime();
    }

    public static String getFormattedNumber(int number){ ;
        DecimalFormat df = new DecimalFormat("#,###");
        if (number < 1000) {
            df = new DecimalFormat("###");
        }
        if (number > 1000 && number < 10000) {
            df = new DecimalFormat("#,###");
        }
        if (number >= 10000) {
            df = new DecimalFormat("##,###");
        }
        return  df.format(number);
    }
    public static String getFormattedNumber(long number){ ;
        DecimalFormat df = new DecimalFormat("#,###");
        if (number < 1000) {
            df = new DecimalFormat("###");
        }
        if (number > 1000 && number < 10000) {
            df = new DecimalFormat("#,###");
        }
        if (number >= 10000) {
            df = new DecimalFormat("##,###");
        }
        return  df.format(number);
    }

    public static String getFormattedNumberInTiyin(int number){

//        DecimalFormat df = new DecimalFormat("#,###", otherSymbols);
        if (number < 1000000) {
             return new DecimalFormat("#,###.##").format(number);
        }
        if (number > 1000000 && number < 10000000) {
            return new DecimalFormat("##,###.##").format(number);
        }
        if (number >= 10000000) {
            return new DecimalFormat("###,###.##").format(number);
        }
        return null;
//        return  df.format(number);
    }

}
