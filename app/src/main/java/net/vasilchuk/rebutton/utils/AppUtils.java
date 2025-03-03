package net.vasilchuk.rebutton.utils;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.provider.Settings;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class AppUtils {

    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            if (ai.enabled) {
                return true;
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return false;
    }

    public static boolean isAccessibilityServiceEnabled(Context context) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> enabledServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);

        for (AccessibilityServiceInfo enabledService : enabledServices) {
            ServiceInfo service = enabledService.getResolveInfo().serviceInfo;
            if (service.packageName.equals("net.vasilchuk.rebutton")) {
                return true;
            }
        }

        return false;
    }

    public static void openAccessibilitySettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void activateApp(Context context, String packageName) {
        context.startActivity(context.getPackageManager().getLaunchIntentForPackage(packageName));
    }

    public static void actionRecentApps(Context context) {
        Intent intent = new Intent(Intent.ACTION_ALL_APPS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setPackage("com.google.android.wearable.sysui");
        context.startActivity(intent);
    }

    public static void actionHome(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }

    public static void actionAssistant(Context context) {
        Intent intent = new Intent("android.intent.action.VOICE_ASSIST");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setPackage("com.google.android.wearable.assistant");
        context.startActivity(intent);
    }

    public static void showMessage(Context context, int stringId) {
        Toast.makeText(context, context.getString(stringId), Toast.LENGTH_SHORT).show();
    }

    public static void showMessage(Context context, int stringId, Object... formatArgs) {
        Toast.makeText(context, context.getString(stringId, formatArgs), Toast.LENGTH_SHORT).show();
    }

    public static HashMap<String, CharSequence> getInstalledApps(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA | PackageManager.PERMISSION_GRANTED);
        HashMap<String, CharSequence> result = new HashMap<>();

        for (ApplicationInfo packageInfo : packages) {
            Intent intent = packageManager.getLaunchIntentForPackage(packageInfo.packageName);

            if (intent != null && !packageInfo.packageName.equals("net.vasilchuk.rebutton")) {
                result.put(packageInfo.packageName, packageInfo.loadLabel(packageManager));
            }
        }

        return result;
    }

    public static void handleButtonClick(Context context, String appId) {
        switch (appId) {
            case "_recent":
                actionRecentApps(context);
                break;

            case "_home":
                actionHome(context);
                break;

            case "_assistant":
                actionAssistant(context);
                break;

            default:
                activateApp(context, appId);
        }
    }
}
