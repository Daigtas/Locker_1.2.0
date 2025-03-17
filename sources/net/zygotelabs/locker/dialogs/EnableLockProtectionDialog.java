package net.zygotelabs.locker.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import android.content.DialogInterface;
import android.view.ViewGroup;
import net.zygotelabs.locker.R;

public class EnableLockProtectionDialog extends DialogFragment {
    public static EnableLockProtectionDialog newInstance() {
        return new EnableLockProtectionDialog();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setView(requireActivity().getLayoutInflater().inflate(R.layout.lock_dialog_layout, (ViewGroup) null, false)).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EnableLockProtectionDialog.this.getTargetFragment().onActivityResult(EnableLockProtectionDialog.this.getTargetRequestCode(), -1, EnableLockProtectionDialog.this.getActivity().getIntent());
            }
        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EnableLockProtectionDialog.this.getTargetFragment().onActivityResult(EnableLockProtectionDialog.this.getTargetRequestCode(), 0, EnableLockProtectionDialog.this.getActivity().getIntent());
            }
        });
        return builder.create();
    }
}
