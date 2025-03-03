package net.vasilchuk.rebutton.ui;
import androidx.appcompat.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.preference.ListPreferenceDialogFragmentCompat;

public class ListPreferenceDialogFragment extends ListPreferenceDialogFragmentCompat {

    @NonNull
    public static ListPreferenceDialogFragment newInstance(String key) {
        ListPreferenceDialogFragment fragment = new ListPreferenceDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

        AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            TextView title = dialog.findViewById(androidx.appcompat.R.id.alertTitle);
            if (title != null) {
                title.setGravity(android.view.Gravity.CENTER);
                title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
        }
    }
}
