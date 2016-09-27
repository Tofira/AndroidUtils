package sample.utils.androidutilssample;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class GeneralUtils {
    private static final Set<String> RTL;
    public static final int GET_SCREEN_HEIGHT = 1;
    public static final int GET_SCREEN_WIDTH = 2;

    public static int getScreenDimen(Context mContext, int whatToGet)
    {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return (whatToGet == GET_SCREEN_HEIGHT) ? size.y : size.x;
    }

    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return (resourceId > 0) ? context.getResources().getDimensionPixelSize(resourceId) : 0;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPx(float dp) {
        return dp * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px){
        return px / (Resources.getSystem().getDisplayMetrics().densityDpi / 160f);
    }

    public static boolean isRTL(Context context)
    {
        if (VersionsUtils.hasJellyBeanMR1()) {
            Configuration config = context.getResources().getConfiguration();

            if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
                return true;
        } else {
            if (isRTL_OlderVersions()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isRTL_OlderVersions()
    {
        Locale locale = Locale.getDefault();

        return locale != null && RTL.contains(locale.getLanguage());
    }

    static
    {
        Set<String> lang = new HashSet<String>();
        lang.add("ar"); // Arabic
        lang.add("dv"); // Divehi
        lang.add("fa"); // Persian (Farsi)
        lang.add("ha"); // Hausa
        lang.add("he"); // Hebrew
        lang.add("iw"); // Hebrew (old code)
        lang.add("ji"); // Yiddish (old code)
        lang.add("ps"); // Pashto, Pushto
        lang.add("ur"); // Urdu
        lang.add("yi"); // Yiddish
        RTL = Collections.unmodifiableSet(lang);
    }

    public static void sendToPlayStore(Context con)
    {
        final String appPackageName = con.getPackageName();
        try {
            con.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException ex) {
            con.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static Calendar getDayStartMillis(Calendar theDay)
    {
        Calendar newDay = Calendar.getInstance();
        newDay.set(theDay.get(Calendar.YEAR), theDay.get(Calendar.MONTH), theDay.get(Calendar.DAY_OF_MONTH),0,0,0);

        long tempMillis = newDay.getTimeInMillis() / 1000;

        newDay.setTimeInMillis(tempMillis*1000);

        return newDay;
    }

    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
