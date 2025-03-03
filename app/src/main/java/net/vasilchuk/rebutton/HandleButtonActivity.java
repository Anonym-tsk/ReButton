package net.vasilchuk.rebutton;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import net.vasilchuk.rebutton.utils.AppUtils;
import net.vasilchuk.rebutton.utils.PreferencesUtils;

public class HandleButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = getApplicationContext();

        String action = getIntent().getAction();
        if (action != null) {
            String appId;
            switch (action) {
                case "android.intent.action.ALL_APPS":
                    appId = PreferencesUtils.getRecentOverride(context);
                    AppUtils.handleButtonClick(context, appId);
                    break;

                case "android.intent.action.VOICE_ASSIST":
                    appId = PreferencesUtils.getVoiceAssistantOverride(context);
                    AppUtils.handleButtonClick(context, appId);
                    break;
            }
        }

        finish();
    }
}

