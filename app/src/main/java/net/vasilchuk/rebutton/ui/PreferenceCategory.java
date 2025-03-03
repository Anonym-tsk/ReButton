package net.vasilchuk.rebutton.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceViewHolder;


public class PreferenceCategory extends androidx.preference.PreferenceCategory {
    public PreferenceCategory(
            @NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public PreferenceCategory(@NonNull Context context, @Nullable AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PreferenceCategory(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PreferenceCategory(@NonNull Context context) {
        super(context, null);
    }

    @Override
    public void onBindViewHolder(@NonNull PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        final TextView titleView = (TextView) holder.findViewById(android.R.id.title);
        titleView.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        titleView.setPadding(0, 0, 0, 0);
    }
}
