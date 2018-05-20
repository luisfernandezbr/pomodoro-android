package br.com.luisfernandez.pomodoro.repo

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import br.com.luisfernandez.pomodoro.entity.PomodoroTask

@Database(entities = arrayOf(PomodoroTask::class), version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun pomodoroTaskDao(): PomodoroTaskDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase::class.java, "pomodoro.db"
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}