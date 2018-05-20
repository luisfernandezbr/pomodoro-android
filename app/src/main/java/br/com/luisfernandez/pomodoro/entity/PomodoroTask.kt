package br.com.luisfernandez.pomodoro.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
data class PomodoroTask(
        @PrimaryKey var id: Long? = null,
        @ColumnInfo(name = "task_duration") val taskDuration: Long,
        @ColumnInfo(name = "finished_date_time") val finishedDateTime: Date
)