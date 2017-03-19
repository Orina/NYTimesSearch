package me.elmira.nytimessearch.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by elmira on 3/16/17.
 */

public class NetworkUtil {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}