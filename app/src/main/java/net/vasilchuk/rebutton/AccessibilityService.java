package net.vasilchuk.rebutton;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.accessibility.AccessibilityEvent;

import net.vasilchuk.rebutton.utils.AppUtils;
import net.vasilchuk.rebutton.utils.PreferencesUtils;

public class AccessibilityService extends android.accessibilityservice.AccessibilityService {
    private static final int DEBOUNCE_TIME = 3000; // debounce time in milliseconds
    private boolean isDebounced = false;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            return;
        }

        final Context context = getApplicationContext();
        String appId = null;

        switch (event.getPackageName().toString()) {
            case "com.google.android.apps.walletnfcrel":
                appId = PreferencesUtils.getGooglePayOverride(context);
                break;

            case "com.google.android.wearable.assistant":
                appId = PreferencesUtils.getGoogleAssistantOverride(context);
                break;
        }

        if (appId == null || appId.equals(PreferencesUtils.PREF_NOTHING)) {
            return;
        }

        if (isDebounced) {
            return;
        }
        isDebounced = true;

        AppUtils.actionHome(context);
        if (!appId.equals(PreferencesUtils.PREF_HOME)) {
            AppUtils.handleButtonClick(context, appId);
        }

        handler.postDelayed(() -> isDebounced = false, DEBOUNCE_TIME);
    }

    @Override
    public void onInterrupt() {}
}
