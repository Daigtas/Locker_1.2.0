package net.zygotelabs.locker;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import androidx.annotation.NonNull;
import android.widget.Toast;
import net.zygotelabs.locker.utils.DeviceAdminManager;

public class DeviceAdmin extends DeviceAdminReceiver {
    private void showToast(Context context, String msg) {
        Toast.makeText(context, msg, 0).show();
    }

    public void onEnabled(Context context, Intent intent) {
        showToast(context, context.getString(R.string.admin_receiver_status_enabled));
    }

    public CharSequence onDisableRequested(Context context, Intent intent) {
        return context.getString(R.string.admin_receiver_status_disable_warning);
    }

    public void onDisabled(Context context, Intent intent) {
        showToast(context, context.getString(R.string.admin_receiver_status_disabled));
    }

    @Override
    public void onPasswordFailed(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle user) {
        super.onPasswordFailed(context, intent, user);
        new DeviceAdminManager(context).failedUnlockAttemptOccurred();
    }
}
