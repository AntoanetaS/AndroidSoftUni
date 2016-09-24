package androidcourse.softuni.bg.batterychange.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import androidcourse.softuni.bg.batterychange.R;
import androidcourse.softuni.bg.batterychange.manager.BatteryManager;
import androidcourse.softuni.bg.batterychange.services.BatteryService;

public class HomeScreen extends AppCompatActivity {
    private static final String TAG = HomeScreen.class.getSimpleName();
    private static final long INITIAL_ALARM_DELAY = 1 * 60 * 60 * 1000L; // 1 hour

    private TextView batteryLevel;
    private static AlarmManager alarmManager;
    private static Intent notificationReceiverIntent;
    private static PendingIntent notificationReceiverPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        batteryLevel = (TextView) findViewById(R.id.tv_batteryLevel);

    }

    @Override
    protected void onStart() {
        super.onStart();

        alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        notificationReceiverIntent = new Intent(getApplicationContext(),
                BatteryService.class);

        // Create an PendingIntent that holds the NotificationReceiverIntent
        notificationReceiverPendingIntent = PendingIntent.getService(
                getApplicationContext(), 0, notificationReceiverIntent, 0);


        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime(),
                INITIAL_ALARM_DELAY,
                notificationReceiverPendingIntent);

            batteryLevel.setText(getString(R.string.battery_level) + " " + BatteryManager.getBatteryBeforeHour(this) + "% Battery after one hour "
                    + BatteryManager.getBatteryAfterHour(this) + "%");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
