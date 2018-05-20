package br.com.luisfernandez.pomodoro.repo

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import br.com.luisfernandez.pomodoro.entity.PomodoroTask

@Dao
interface PomodoroTaskDao {

    @Query("SELECT * FROM PomodoroTask")
    fun getAll(): List<PomodoroTask>

    @Insert
    fun insertAll(vararg pomodoroTaskList: PomodoroTask)
}