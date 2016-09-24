package androidcourse.softuni.bg.batterychange.services;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.os.BatteryManager;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class BatteryService extends IntentService {
    private BatteryStatusReceiver batteryStatusReceiver;
    private static int batteryAfterOneHour = 0;
    public BatteryService() {
        super("BatteryService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        batteryStatusReceiver = new BatteryStatusReceiver();
        registerReceiver(batteryStatusReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    public static class BatteryStatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            androidcourse.softuni.bg.batterychange.manager.BatteryManager.saveBatteryBeforeHour(context,
                    androidcourse.softuni.bg.batterychange.manager.BatteryManager.getBatteryAfterHour(context));
            batteryAfterOneHour = level;
            if(batteryAfterOneHour != 0) {
                androidcourse.softuni.bg.batterychange.manager.BatteryManager.saveBatteryAfterHour(context, batteryAfterOneHour);
            }

        }
    }

}
