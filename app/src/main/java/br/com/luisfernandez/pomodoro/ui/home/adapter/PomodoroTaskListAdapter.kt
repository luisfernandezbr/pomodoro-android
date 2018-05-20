package br.com.luisfernandez.pomodoro.ui.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.luisfernandez.pomodoro.R
import br.com.luisfernandez.pomodoro.entity.PomodoroTask
import java.text.SimpleDateFormat
import java.util.*

class PomodoroTaskListAdapter: RecyclerView.Adapter<PomodoroTaskListAdapter.PomodoroTaskVH>() {

    val pomodoroTaskList: ArrayList<PomodoroTask> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PomodoroTaskVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pomodoro_task, parent, false)
        return PomodoroTaskVH(view)
    }

    override fun getItemCount(): Int {
        return pomodoroTaskList.size
    }

    override fun onBindViewHolder(holder: PomodoroTaskVH, position: Int) {
        var item = pomodoroTaskList[position]

        holder.textTime.text = item.taskDuration.toString()
        holder.textStatus.text = if (item.taskDuration > 80000) "Finished" else "Stopped"
        holder.textDate.text = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(item.finishedDateTime)
    }

    fun add(pomodoroTaskList: List<PomodoroTask>) {
        this.pomodoroTaskList.addAll(pomodoroTaskList)
        this.notifyDataSetChanged()
    }

    class PomodoroTaskVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textTime = itemView.findViewById<TextView>(R.id.textTime)
        var textStatus = itemView.findViewById<TextView>(R.id.textStatus)
        var textDate = itemView.findViewById<TextView>(R.id.textDate)
    }
}