package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

class DrawableCompatBase {
    DrawableCompatBase() {
    }

    public static void setTint(Drawable drawable, int tint) {
        if (drawable instanceof TintAwareDrawable) {
            ((TintAwareDrawable) drawable).setTint(tint);
        }
    }

    public static void setTintList(Drawable drawable, ColorStateList tint) {
        if (drawable instanceof TintAwareDrawable) {
            ((TintAwareDrawable) drawable).setTintList(tint);
        }
    }

    public static void setTintMode(Drawable drawable, PorterDuff.Mode tintMode) {
        if (drawable instanceof TintAwareDrawable) {
            ((TintAwareDrawable) drawable).setTintMode(tintMode);
        }
    }

    public static Drawable wrapForTinting(Drawable drawable) {
        if (!(drawable instanceof TintAwareDrawable)) {
            return new DrawableWrapperDonut(drawable);
        }
        return drawable;
    }

    public static void inflate(Drawable drawable, Resources res, XmlPullParser parser, AttributeSet attrs, Resources.Theme t) throws IOException, XmlPullParserException {
        drawable.inflate(res, parser, attrs);
    }
}
