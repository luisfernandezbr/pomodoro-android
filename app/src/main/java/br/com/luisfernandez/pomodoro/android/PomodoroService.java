package br.com.luisfernandez.pomodoro.android;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import br.com.luisfernandez.pomodoro.Pomodoro;

public class PomodoroService extends Service {

    public static final String TAG = "PomodoroService";
    public static final String ACTION_ON_TICK = "br.com.luisfernandez.pomodoro.ON_TICK";
    public static final String ACTION_ON_FINISH = "br.com.luisfernandez.pomodoro.ON_FINISH";

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
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d(TAG, "onTaskRemoved: ");
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

    @Override
    public void onCreate() {
        super.onCreate();
        countDownTimer = new CountDownTimer(Pomodoro.POMODORO_TIME_IN_MILLIS, Pomodoro.TIMER_COUNT_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "onTick: " + millisUntilFinished);

                Intent intent = new Intent(ACTION_ON_TICK);
                intent.putExtra("current_time", millisUntilFinished);
                sendBroadcast(intent);
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish: ");
                Intent intent = new Intent(ACTION_ON_FINISH);
                sendBroadcast(intent);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");

        countDownTimer.start();


        return super.onStartCommand(intent, flags, startId);
    }
}
