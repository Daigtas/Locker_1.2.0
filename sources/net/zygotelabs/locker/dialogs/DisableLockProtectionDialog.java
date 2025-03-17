package net.zygotelabs.locker.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import android.content.DialogInterface;
import android.view.ViewGroup;
import net.zygotelabs.locker.R;

public class DisableLockProtectionDialog extends DialogFragment {
    public static DisableLockProtectionDialog newInstance() {
        DisableLockProtectionDialog dialog = new DisableLockProtectionDialog();
        dialog.setArguments(new Bundle());
        return dialog;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setView(requireActivity().getLayoutInflater().inflate(R.layout.disable_lock_dialog_layout, (ViewGroup) null, false)).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DisableLockProtectionDialog.this.getTargetFragment().onActivityResult(DisableLockProtectionDialog.this.getTargetRequestCode(), -1, DisableLockProtectionDialog.this.getActivity().getIntent());
            }
        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DisableLockProtectionDialog.this.getTargetFragment().onActivityResult(DisableLockProtectionDialog.this.getTargetRequestCode(), 0, DisableLockProtectionDialog.this.getActivity().getIntent());
            }
        });
        return builder.create();
    }
}
