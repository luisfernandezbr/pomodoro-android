package br.com.luisfernandez.pomodoro

import java.util.concurrent.TimeUnit

class Pomodoro {
    companion object {
        const val POMODORO_TIME_IN_MILLIS = 20 * 1000L
        const val TIMER_COUNT_INTERVAL = 1000L
        const val INTERVAL_TO_SHOW_NORMAL_TIME = 1000 * 60 * 60 * 3

        fun getFormattedTime(timeInMillis: Long): String {
            val toMinutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis)
            val toSeconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis) - TimeUnit.MINUTES.toSeconds(toMinutes)
            return String.format("%02d:%02d", toMinutes, toSeconds)
        }
    }
}