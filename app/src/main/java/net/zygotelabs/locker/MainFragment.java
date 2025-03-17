package net.zygotelabs.locker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import net.zygotelabs.locker.dialogs.DisableLockProtectionDialog;
import net.zygotelabs.locker.dialogs.EnableLockProtectionDialog;
import net.zygotelabs.locker.utils.DeviceAdminManager;

public class MainFragment extends Fragment {
    private static final int DISABLE_PROTECTION_DIALOG_FRAGMENT = 6;
    private static final int ENABLE_PROTECTION_DIALOG_FRAGMENT = 5;
    protected static final int REQUEST_CODE_ENABLE_ADMIN = 1;
    private Button button;
    private CheckBox checkBox;
    private CheckBox checkBoxHideWarning;
    private CheckBox checkBoxSafeMode;
    private DeviceAdminManager dam;
    /* access modifiers changed from: private */
    public SharedPreferences.Editor editor;
    private RelativeLayout layoutSafeMode;
    /* access modifiers changed from: private */
    public SeekBar lockProgress;
    private int mStackLevel = 0;
    /* access modifiers changed from: private */
    public TextView seekTextValue;
    private SharedPreferences settings;
    private RelativeLayout statusLayout;
    private TextView statusTextSummary;
    private TextView statusTextTitle;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.mStackLevel = savedInstanceState.getInt("level");
        }
        this.dam = new DeviceAdminManager(getActivity());
        this.settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        this.editor = this.settings.edit();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("level", this.mStackLevel);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        this.checkBox = (CheckBox) rootView.findViewById(R.id.checkBoxAdmin);
        this.checkBoxHideWarning = (CheckBox) rootView.findViewById(R.id.checkBoxHideWarning);
        this.checkBoxSafeMode = (CheckBox) rootView.findViewById(R.id.checkBoxSafeMode);
        this.layoutSafeMode = (RelativeLayout) rootView.findViewById(R.id.safety_layout);
        this.button = (Button) rootView.findViewById(R.id.buttonApply);
        this.statusLayout = (RelativeLayout) rootView.findViewById(R.id.top_layout);
        this.statusTextTitle = (TextView) rootView.findViewById(R.id.textViewTopTitle);
        this.statusTextSummary = (TextView) rootView.findViewById(R.id.textViewTopTitleSummary);
        this.seekTextValue = (TextView) rootView.findViewById(R.id.textViewLockerCount);
        this.lockProgress = (SeekBar) rootView.findViewById(R.id.seekBarLocker);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.checkBoxHideWarning.setVisibility(View.INVISIBLE);
            this.checkBoxHideWarning.setChecked(false);
            this.layoutSafeMode.setVisibility(View.INVISIBLE);
            this.checkBoxSafeMode.setChecked(false);
            this.editor.putBoolean("safeMode", false);
            this.editor.putBoolean("hideWarning", false);
            this.editor.apply();
        }
        this.lockProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    progress++;
                    MainFragment.this.lockProgress.setProgress(progress);
                }
                MainFragment.this.seekTextValue.setText(String.valueOf(progress));
                MainFragment.this.editor.putInt("unlockLimit", MainFragment.this.lockProgress.getProgress());
                MainFragment.this.editor.commit();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        updateAdminCheck();
        return rootView;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateAdminCheck();
        switch (requestCode) {
            case 5:
                if (resultCode == -1) {
                    enableLockProtection();
                    return;
                } else {
                    if (resultCode == 0) {
                    }
                    return;
                }
            case 6:
                if (resultCode == -1) {
                    disableLockProtection();
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void onCheckBoxClicked(boolean checked) {
        if (checked) {
            startActivityForResult(this.dam.getStartAdminEnableIntent(), 1);
        } else if (this.dam.removeAdminRole()) {
            adjustAdminUI(false);
        }
    }

    public void onSafeModeCheckBoxClicked(boolean checked) {
        if (checked) {
            this.checkBoxHideWarning.setChecked(true);
            this.checkBoxHideWarning.setEnabled(false);
            return;
        }
        this.checkBoxHideWarning.setEnabled(true);
    }

    private void updateAdminCheck() {
        adjustAdminUI(this.dam.isActiveAdmin());
        this.lockProgress.setProgress(this.settings.getInt("unlockLimit", 5));
    }

    private void updateLockStatus() {
        if (this.dam.isProtected()) {
            int unlockLimit = this.settings.getInt("unlockLimit", 5);
            if (this.settings.getBoolean("safeMode", false)) {
                this.statusTextTitle.setText(getActivity().getString(R.string.protected_safe_mode));
                this.statusTextSummary.setText(getActivity().getString(R.string.protected_safe_mode_summary));
                this.statusLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorOrange));
            } else {
                this.statusLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorGreen));
                this.statusTextTitle.setText(getActivity().getString(R.string.protect));
                this.statusTextSummary.setText(getActivity().getString(R.string.protected_summary_one) + " " + Integer.toString(unlockLimit) + " " + getActivity().getString(R.string.protected_summary_two));
            }
            this.button.setText(getActivity().getString(R.string.disable));
            this.checkBoxHideWarning.setChecked(this.settings.getBoolean("hideWarning", false));
            this.checkBoxSafeMode.setChecked(this.settings.getBoolean("safeMode", false));
            this.checkBoxHideWarning.setEnabled(false);
            this.checkBoxSafeMode.setEnabled(false);
            this.lockProgress.setEnabled(false);
            return;
        }
        this.statusLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
        this.statusTextTitle.setText(getActivity().getString(R.string.not_protected));
        this.statusTextSummary.setText(getActivity().getString(R.string.not_protected_summary));
        this.button.setText(getActivity().getString(R.string.enable));
        this.lockProgress.setEnabled(true);
        if (!this.checkBoxSafeMode.isChecked()) {
            this.checkBoxHideWarning.setEnabled(true);
        }
        this.checkBoxSafeMode.setEnabled(true);
    }

    private void adjustAdminUI(boolean adminState) {
        this.checkBox.setChecked(adminState);
        this.button.setEnabled(adminState);
        if (!adminState) {
            this.editor.putBoolean("lockEnabled", false);
            this.editor.commit();
        }
        updateLockStatus();
    }

    public void toggleLockProtection() {
        if (this.settings.getBoolean("lockEnabled", false)) {
            showDisableProtectionDialog();
        } else {
            showEnableProtectionDialog();
        }
    }

    private void enableLockProtection() {
        if (this.dam.isActiveAdmin()) {
            if (this.dam.enableLockScreenProtection(this.lockProgress.getProgress(), this.checkBoxHideWarning.isChecked(), this.checkBoxSafeMode.isChecked())) {
                this.editor.putInt("unlockLimit", this.lockProgress.getProgress());
                this.editor.putBoolean("lockEnabled", true);
                this.editor.putBoolean("hideWarning", this.checkBoxHideWarning.isChecked());
                this.editor.putBoolean("safeMode", this.checkBoxSafeMode.isChecked());
                this.editor.commit();
            } else {
                this.checkBoxHideWarning.setChecked(false);
                this.checkBoxSafeMode.setChecked(false);
            }
        }
        updateAdminCheck();
    }

    private void showEnableProtectionDialog() {
        mStackLevel++;
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        Fragment prev = getParentFragmentManager().findFragmentByTag("EnableLockProtectionDialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        ft.commit();
        DialogFragment dialogFrag = EnableLockProtectionDialog.newInstance();
        dialogFrag.setTargetFragment(this, ENABLE_PROTECTION_DIALOG_FRAGMENT);
        dialogFrag.show(getParentFragmentManager(), "EnableLockProtectionDialog");
    }

    private void disableLockProtection() {
        if (this.dam.removeAdminRole()) {
            this.editor.putBoolean("lockEnabled", false);
            this.editor.commit();
            this.checkBox.setChecked(false);
            adjustAdminUI(false);
        }
    }

    private void showDisableProtectionDialog() {
        mStackLevel++;
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        Fragment prev = getParentFragmentManager().findFragmentByTag("DisableLockProtectionDialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        ft.commit();
        DialogFragment dialogFrag = DisableLockProtectionDialog.newInstance();
        dialogFrag.setTargetFragment(this, DISABLE_PROTECTION_DIALOG_FRAGMENT);
        dialogFrag.show(getParentFragmentManager(), "DisableLockProtectionDialog");
    }
}
