package sample.utils.androidutilssample;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Mickael on 27/09/2016.
 */

public class ColorsUtils {

    public static final String TRANSPARENT_COLOR_00 = "00";
    public static final String TRANSPARENT_COLOR_10 = "19";
    public static final String TRANSPARENT_COLOR_20 = "33";
    public static final String TRANSPARENT_COLOR_30 = "4C";
    public static final String TRANSPARENT_COLOR_40 = "66";
    public static final String TRANSPARENT_COLOR_50 = "80";
    public static final String TRANSPARENT_COLOR_60 = "9A";
    public static final String TRANSPARENT_COLOR_70 = "B3";
    public static final String TRANSPARENT_COLOR_85 = "D8";
    public static final String TRANSPARENT_COLOR_80 = "CC";
    public static final String TRANSPARENT_COLOR_90 = "E5";
    public static final String TRANSPARENT_COLOR_95 = "F2";
    public static final String FULL_COLOR = "ff";

    public static int getTransparentColor (String transparency, int originalColor)
    {
        String currentColorString = "#" + Integer.toHexString(originalColor);
        String transparentStr = currentColorString.replaceFirst(FULL_COLOR, transparency);
        return Color.parseColor(transparentStr);
    }

    public static void animateImageViewBackground(final View viewToAnimate, int colorFrom, int colorTo, int animationDuration)
    {

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);

        colorAnimation.setDuration(animationDuration);

        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int newColor = (int) animator.getAnimatedValue();
                if(viewToAnimate instanceof ImageView)
                    setImageViewColor((ImageView) viewToAnimate, newColor);
                else
                    viewToAnimate.setBackgroundColor(newColor);
            }
        });
        colorAnimation.start();
    }

    private static void setImageViewColor(ImageView imageView,int color)
    {
        Bitmap image = Bitmap.createBitmap(imageView.getWidth(), imageView.getHeight(), Bitmap.Config.RGB_565);
        image.eraseColor(color);
        imageView.setImageBitmap(image);
    }

    public static void changeImageViewDrawableColor(ImageView imageView, int newColor)
    {
        imageView.setImageDrawable(changeDrawableColor(imageView.getDrawable(), newColor));
    }

    public static Drawable changeDrawableColor(Drawable drawable, int newColor)
    {
        drawable.setColorFilter(new PorterDuffColorFilter(newColor, PorterDuff.Mode.SRC_IN));
        return drawable;
    }

}
