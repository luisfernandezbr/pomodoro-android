package br.com.luisfernandez.pomodoro.android;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.style.EasyEditSpan;
import android.util.Log;

import com.iamhabib.easy_preference.EasyPreference;

import br.com.luisfernandez.pomodoro.Pomodoro;

public class PomodoroService extends Service {

    public static final String TAG = "PomodoroService";
    public static final String ACTION_ON_TICK = "br.com.luisfernandez.pomodoro.ON_TICK";
    public static final String ACTION_ON_FINISH = "br.com.luisfernandez.pomodoro.ON_FINISH";

    public static void start(Context context) {
        Intent intent = new Intent(context, PomodoroService.class);
        context.startService(intent);
    }

    public static void stop(Context context) {
        Intent intent = new Intent(context, PomodoroService.class);
        context.stopService(intent);
    }

    public PomodoroService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d(TAG, "onTaskRemoved: ");

        if (currentCounter > -1) {
            EasyPreference.with(getApplicationContext())
                    .addLong("current_value", currentCounter)
                    .save();
        }
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind: ");
    }

    @Override
    public ComponentName startForegroundService(Intent service) {
        Log.d(TAG, "startForegroundService: ");
        return super.startForegroundService(service);
    }

    @Override
    public ComponentName startService(Intent service) {
        Log.d(TAG, "startService: ");
        return super.startService(service);
    }

    private CountDownTimer countDownTimer;
    private long currentCounter;

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");


        final long persistedValue = EasyPreference.with(getApplicationContext()).getLong("current_value", -1);

        long startAt = Pomodoro.POMODORO_TIME_IN_MILLIS;

        if (persistedValue > -1) {
            startAt = persistedValue;
        }

        countDownTimer = new CountDownTimer(startAt, Pomodoro.TIMER_COUNT_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "onTick: " + millisUntilFinished);

                currentCounter = millisUntilFinished;

                Intent intent = new Intent(ACTION_ON_TICK);
                intent.putExtra("current_time", millisUntilFinished);
                sendBroadcast(intent);
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish: ");
                currentCounter = -1;

                EasyPreference.with(getApplicationContext()).clearAll().save();

                Intent intent = new Intent(ACTION_ON_FINISH);
                sendBroadcast(intent);
                stopSelf();

            }
        };
        countDownTimer.start();


        return START_STICKY;
        //return START_NOT_STICKY;
    }
}
