package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.widget.CompoundButton;

class CompoundButtonCompatLollipop {
    CompoundButtonCompatLollipop() {
    }

    static void setButtonTintList(CompoundButton button, ColorStateList tint) {
        button.setButtonTintList(tint);
    }

    static ColorStateList getButtonTintList(CompoundButton button) {
        return button.getButtonTintList();
    }

    static void setButtonTintMode(CompoundButton button, PorterDuff.Mode tintMode) {
        button.setButtonTintMode(tintMode);
    }

    static PorterDuff.Mode getButtonTintMode(CompoundButton button) {
        return button.getButtonTintMode();
    }
}
