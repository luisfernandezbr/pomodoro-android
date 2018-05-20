package br.com.luisfernandez.pomodoro.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.luisfernandez.pomodoro.android.AppApplication
import br.com.luisfernandez.pomodoro.entity.PomodoroTask
import br.com.luisfernandez.pomodoro.model.db.AppDatabase
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PomodoroTaskViewModel : ViewModel() {

    open val pomodoroTaskList: MutableLiveData<List<PomodoroTask>> = MutableLiveData()

    fun loadPomodoroTaskList(): LiveData<List<PomodoroTask>> {
        val pomodoroTaskDao = AppDatabase
                .getInstance(AppApplication.context)
                ?.pomodoroTaskDao()

        doAsync {
            var result = pomodoroTaskDao?.getAll()
            uiThread {
                pomodoroTaskList.value = result
            }
        }

        return pomodoroTaskList
    }

    fun insertPomodoroTask(pomodoroTask: PomodoroTask) {
        doAsync {
            val pomodoroTaskDao = AppDatabase
                    .getInstance(AppApplication.context)
                    ?.pomodoroTaskDao()
            pomodoroTaskDao
                    ?.insertAll(
                            pomodoroTask
                    )
        }


    }
}
