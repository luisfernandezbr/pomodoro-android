package br.com.luisfernandez.pomodoro.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.AsyncTask
import br.com.luisfernandez.pomodoro.AppApplication
import br.com.luisfernandez.pomodoro.entity.PomodoroTask
import br.com.luisfernandez.pomodoro.repo.AppDatabase
import br.com.luisfernandez.pomodoro.repo.PomodoroTaskDao

class PomodoroTaskViewModel : ViewModel() {

    open val pomodoroTaskList: MutableLiveData<List<PomodoroTask>> = MutableLiveData()

    fun loadPomodoroTaskList(): LiveData<List<PomodoroTask>> {
        val pomodoroTaskDao = AppDatabase
                .getInstance(AppApplication.context)
                ?.pomodoroTaskDao()



        Thread(
                object :Runnable {
                    override fun run() {
                        val all = pomodoroTaskDao?.getAll()
                        pomodoroTaskList.value = all
                    }

                }
        ).start()
//        pomodoroTaskDao
//                ?.insertAll(
//                        PomodoroTask(
//                                taskDuration = 5000,
//                                finishedDateTime = Date().toString()
//                        )
//                )



        return pomodoroTaskList
    }

    internal class MyAsyncTask(val pomodoroTaskDao: PomodoroTaskDao) : AsyncTask<Void, Void, List<PomodoroTask>>() {

        override fun doInBackground(vararg voids: Void): List<PomodoroTask>? {
            return pomodoroTaskDao?.getAll()
        }

        override fun onPostExecute(pomodoroTasks: List<PomodoroTask>) {
            super.onPostExecute(pomodoroTasks)

        }
    }
}
