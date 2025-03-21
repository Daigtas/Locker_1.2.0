package android.support.v4.app;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

abstract class BaseFragmentActivityHoneycomb extends BaseFragmentActivityEclair {
    BaseFragmentActivityHoneycomb() {
    }

    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View v = dispatchFragmentsOnCreateView(parent, name, context, attrs);
        if (v != null || Build.VERSION.SDK_INT < 11) {
            return v;
        }
        return super.onCreateView(parent, name, context, attrs);
    }
}
