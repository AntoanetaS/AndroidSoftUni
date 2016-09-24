package androidcourse.softuni.bg.batterychange.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class BatteryManager {
    public static final String KEY_BATTERY_BEFORE_HOUR = "KEY_BATTERY_BEFORE_HOUR";
    public static final String KEY_BATTERY_AFTER_HOUR = "KEY_BATTERY_AFTER_HOUR";

    public static void saveBatteryBeforeHour(Context context, int percent) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_BATTERY_BEFORE_HOUR, percent);
        editor.commit();
    }

    public static int getBatteryBeforeHour(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(KEY_BATTERY_BEFORE_HOUR, 0);
    }

    public static void saveBatteryAfterHour(Context context, int percent) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_BATTERY_AFTER_HOUR, percent);
        editor.commit();
    }

    public static int getBatteryAfterHour(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(KEY_BATTERY_AFTER_HOUR, 0);
    }

    private BatteryManager() {
    }
}
