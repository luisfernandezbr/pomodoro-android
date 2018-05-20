package br.com.luisfernandez.pomodoro

import android.app.Application

class AppApplication : Application() {
    companion object {
        lateinit var context: AppApplication
    }

    override fun onCreate() {
        super.onCreate()

        context = this
    }

}