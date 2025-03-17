package android.support.v4.app;

abstract class BaseFragmentActivityEclair extends BaseFragmentActivityDonut {
    BaseFragmentActivityEclair() {
    }

    /* access modifiers changed from: package-private */
    public void onBackPressedNotHandled() {
        super.onBackPressed();
    }
}
