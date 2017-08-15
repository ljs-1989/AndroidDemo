package android.cx.com.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;

/**
 * Created by qyc on 2016/3/24.
 */
public class DimenUtils {
    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static int calcTextHeight(Paint paint, String demoText) {

        Rect r = new Rect();
        paint.getTextBounds(demoText, 0, demoText.length(), r);
        return r.height();
    }
    /*
     获取屏幕宽
     */
    private static int screenWidth;

    public static int getScreenWidth(Context mContext) {
        if (screenWidth != 0) {
            return screenWidth;
        }
        DisplayMetrics metrics = new DisplayMetrics();
        ( (Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        return  screenWidth;
    }

    /*
    获取屏幕高
    */
    private static int screenHeight;

    public static int getScreenHeight(Context mContext) {
        if (screenHeight != 0) {
            return screenHeight;
        }
        DisplayMetrics metrics = new DisplayMetrics();
        ( (Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenHeight = metrics.heightPixels;

        return  screenHeight;
    }
}
