package net.zygotelabs.locker.utils;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;
import net.zygotelabs.locker.DeviceAdmin;
import net.zygotelabs.locker.R;

public class DeviceAdminManager {
    private Context context;
    private DevicePolicyManager mDPM;
    private ComponentName mDeviceAdmin;
    private SharedPreferences settings;

    public DeviceAdminManager(Context context2) {
        this.context = context2;
        this.mDeviceAdmin = new ComponentName(context2, DeviceAdmin.class);
        this.mDPM = (DevicePolicyManager) context2.getSystemService("device_policy");
        this.settings = PreferenceManager.getDefaultSharedPreferences(context2);
    }

    public boolean isActiveAdmin() {
        return this.mDPM.isAdminActive(this.mDeviceAdmin);
    }

    public Intent getStartAdminEnableIntent() {
        Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
        intent.putExtra("android.app.extra.DEVICE_ADMIN", this.mDeviceAdmin);
        intent.putExtra("android.app.extra.ADD_EXPLANATION", this.context.getString(R.string.add_admin_extra_app_text));
        return intent;
    }

    public boolean removeAdminRole() {
        try {
            this.mDPM.removeActiveAdmin(this.mDeviceAdmin);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean enableLockScreenProtection(int maxAttempts, boolean hideWarning, boolean safeMode) {
        if (hideWarning || safeMode) {
            try {
                getNumberOfFailedUnlockAttempts();
                return true;
            } catch (Exception e) {
                Toast.makeText(this.context, this.context.getString(R.string.hide_lockscreen_warning_error), 1).show();
                return false;
            }
        } else {
            try {
                this.mDPM.setMaximumFailedPasswordsForWipe(this.mDeviceAdmin, maxAttempts);
                return true;
            } catch (Exception e2) {
                return false;
            }
        }
    }

    private int getNumberOfFailedUnlockAttempts() {
        return this.mDPM.getCurrentFailedPasswordAttempts();
    }

    public void failedUnlockAttemptOccurred() {
        if (isProtected() && getNumberOfFailedUnlockAttempts() >= this.settings.getInt("unlockLimit", 5)) {
            if (!this.settings.getBoolean("safeMode", false)) {
                this.mDPM.wipeData(0);
            } else {
                new NotificationHandler().createTestModeWipeNotification(this.context);
            }
        }
    }

    public boolean isProtected() {
        return this.settings.getBoolean("lockEnabled", false);
    }
}
