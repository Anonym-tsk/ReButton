package net.vasilchuk.rebutton;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import net.vasilchuk.rebutton.ui.ListPreferenceDialogFragment;
import net.vasilchuk.rebutton.utils.AppUtils;
import net.vasilchuk.rebutton.utils.PreferencesUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View rootView = findViewById(R.id.root_layout);
        if (getResources().getConfiguration().isScreenRound()) {
            final int padding = getResources().getDimensionPixelSize(R.dimen.padding_bottom_round);
            rootView.setPadding(0, 0, 0, padding);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.preferences, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        private void addEntriesToList(ListPreference preference, Map<String, CharSequence> installedApps) {
            CharSequence[] recentEntries = Stream.concat(Arrays.stream(preference.getEntries()), Arrays.stream(installedApps.values().toArray())).toArray(CharSequence[]::new);
            CharSequence[] recentValues = Stream.concat(Arrays.stream(preference.getEntryValues()), Arrays.stream(installedApps.keySet().toArray())).toArray(CharSequence[]::new);
            preference.setEntries(recentEntries);
            preference.setEntryValues(recentValues);
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);

            final Context context = getPreferenceScreen().getContext();
            final Map<String, CharSequence> installedApps = AppUtils.getInstalledApps(context);

            ListPreference recentPreference = findPreference(PreferencesUtils.KEY_PREF_RECENT_APPS);
            addEntriesToList(recentPreference, installedApps);

            ListPreference voiceAssistantPreference = findPreference(PreferencesUtils.KEY_PREF_VOICE_ASSISTANT);
            addEntriesToList(voiceAssistantPreference, installedApps);

            ListPreference payPreference = findPreference(PreferencesUtils.KEY_PREF_GOOGLE_PAY);
            addEntriesToList(payPreference, installedApps);

            ListPreference googleAssistantPreference = findPreference(PreferencesUtils.KEY_PREF_GOOGLE_ASSISTANT);
            addEntriesToList(googleAssistantPreference, installedApps);
        }

        @Override
        public void onDisplayPreferenceDialog(@NonNull Preference preference) {
            if (preference instanceof ListPreference) {
                if (preference.getKey().equals("google_pay") || preference.getKey().equals("google_assistant")) {
                    Context context = getContext();
                    if (!AppUtils.isAccessibilityServiceEnabled(context)) {
                        AppUtils.openAccessibilitySettings(context);
                        AppUtils.showMessage(context, R.string.need_accessibility_service);
                        return;
                    }
                }

                ListPreferenceDialogFragment dialogFragment =
                        ListPreferenceDialogFragment.newInstance(preference.getKey());
                dialogFragment.setTargetFragment(this, 0);
                dialogFragment.show(getParentFragmentManager(), "ListPreferenceDialog");
            } else {
                super.onDisplayPreferenceDialog(preference);
            }
        }
    }
}

