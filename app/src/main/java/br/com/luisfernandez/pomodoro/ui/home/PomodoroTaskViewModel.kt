package br.com.luisfernandez.pomodoro.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.AsyncTask
import br.com.luisfernandez.pomodoro.AppApplication
import br.com.luisfernandez.pomodoro.entity.PomodoroTask
import br.com.luisfernandez.pomodoro.repo.AppDatabase
import br.com.luisfernandez.pomodoro.repo.PomodoroTaskDao
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PomodoroTaskViewModel : ViewModel() {

    open val pomodoroTaskList: MutableLiveData<List<PomodoroTask>> = MutableLiveData()

    fun loadPomodoroTaskList(): LiveData<List<PomodoroTask>> {
        val pomodoroTaskDao = AppDatabase
                .getInstance(AppApplication.context)
                ?.pomodoroTaskDao()

//        pomodoroTaskDao
//                ?.insertAll(
//                        PomodoroTask(
//                                taskDuration = 5000,
//                                finishedDateTime = Date().toString()
//                        )
//                )

        doAsync {
            var result = pomodoroTaskDao?.getAll()
            uiThread {
                pomodoroTaskList.value = result
            }
        }

        return pomodoroTaskList
    }
}
