package br.com.luisfernandez.pomodoro.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.luisfernandez.pomodoro.Pomodoro
import br.com.luisfernandez.pomodoro.android.AppApplication
import br.com.luisfernandez.pomodoro.entity.PomodoroTask
import br.com.luisfernandez.pomodoro.model.db.AppDatabase
import br.com.luisfernandez.pomodoro.ui.home.pojo.ItemList
import br.com.luisfernandez.pomodoro.ui.home.pojo.ItemListContent
import br.com.luisfernandez.pomodoro.ui.home.pojo.ItemListTitle
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat
import java.util.*

class PomodoroTaskViewModel : ViewModel() {

    open val pomodoroTaskList: MutableLiveData<List<ItemList>> = MutableLiveData()

    fun loadPomodoroTaskList(): LiveData<List<ItemList>> {
        val pomodoroTaskDao = AppDatabase
                .getInstance(AppApplication.context)
                ?.pomodoroTaskDao()

        doAsync {
            val result = pomodoroTaskDao?.getAll()

            val list: MutableList<ItemList> = ArrayList()

            var lastDate: String? = null

            for (pomodoroTask in result!!) {
                val itemListContent = ItemListContent()
                itemListContent.elapsedTime = Pomodoro.getFormattedTime(pomodoroTask.taskDuration)
                itemListContent.status = if (pomodoroTask.taskDuration == Pomodoro.POMODORO_TIME_IN_MILLIS) "Finished" else "Stopped"
                itemListContent.finishedDate = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(pomodoroTask.finishedDateTime)

                var date = SimpleDateFormat("dd/MM/yyyy HH", Locale.getDefault()).format(pomodoroTask.finishedDateTime)

                if (lastDate == null || lastDate != date) {
                    val itemListTitle = ItemListTitle()
                    itemListTitle.title = date

                    list.add(itemListTitle)
                    lastDate = date
                }

                list.add(itemListContent)
            }

            uiThread {
                pomodoroTaskList.value = list
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
