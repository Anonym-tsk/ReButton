package net.vasilchuk.rebutton.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class PreferencesUtils {
    public static final String KEY_PREF_RECENT_APPS = "recent_apps";
    public static final String KEY_PREF_VOICE_ASSISTANT = "voice_assistant";
    public static final String KEY_PREF_GOOGLE_PAY = "google_pay";
    public static final String KEY_PREF_GOOGLE_ASSISTANT = "google_assistant";
    public static final String PREF_NOTHING = "_nothing";
    public static final String PREF_HOME = "_home";

    public static String getRecentOverride(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(KEY_PREF_RECENT_APPS, "_recent");
    }

    public static String getVoiceAssistantOverride(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(KEY_PREF_VOICE_ASSISTANT, "_assistant");
    }

    public static String getGooglePayOverride(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(KEY_PREF_GOOGLE_PAY, PREF_NOTHING);
    }

    public static String getGoogleAssistantOverride(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(KEY_PREF_GOOGLE_ASSISTANT, PREF_NOTHING);
    }
}
