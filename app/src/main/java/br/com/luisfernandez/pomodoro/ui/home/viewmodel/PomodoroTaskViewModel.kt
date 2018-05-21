package br.com.luisfernandez.pomodoro.ui.home.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.text.format.DateUtils
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


                var formattedDate: String? = null
                // https://developer.android.com/reference/android/text/format/DateUtils.html
                formattedDate = if (Date().time - pomodoroTask.finishedDateTime.time < Pomodoro.INTERVAL_TO_SHOW_NORMAL_TIME) {
                    DateUtils.getRelativeTimeSpanString(
                            pomodoroTask.finishedDateTime.time,
                            Date().time,
                            DateUtils.MINUTE_IN_MILLIS
                    ).toString()
                } else {
                    SimpleDateFormat("HH:mm", Locale.getDefault()).format(pomodoroTask.finishedDateTime)
                }

                itemListContent.finishedDate = formattedDate

                var date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(pomodoroTask.finishedDateTime)

                if (lastDate == null || lastDate != date) {
                    val itemListTitle = ItemListTitle()
                    pomodoroTask.finishedDateTime.time
                    val formattedTitle = DateUtils.getRelativeDateTimeString(
                            AppApplication.context,
                            pomodoroTask.finishedDateTime.time,
                            DateUtils.DAY_IN_MILLIS,
                            DateUtils.WEEK_IN_MILLIS,
                            0
                    ).toString()

                    // Works only in portuguese
                    itemListTitle.title = formattedTitle.substring(0, formattedTitle.length - 5)

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
