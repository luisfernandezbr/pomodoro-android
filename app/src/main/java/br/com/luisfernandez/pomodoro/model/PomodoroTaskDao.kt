package br.com.luisfernandez.pomodoro.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import br.com.luisfernandez.pomodoro.entity.PomodoroTask

@Dao
interface PomodoroTaskDao {

    @Query("SELECT * FROM PomodoroTask ORDER BY finished_date_time DESC")
    fun getAll(): List<PomodoroTask>

    @Insert
    fun insertAll(vararg pomodoroTaskList: PomodoroTask)
}