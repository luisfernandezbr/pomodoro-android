package br.com.luisfernandez.pomodoro

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.luisfernandez.pomodoro.entity.PomodoroTask

class PomodoroTaskListAdapter(
        val pomodoroTaskList: List<PomodoroTask>
): RecyclerView.Adapter<PomodoroTaskListAdapter.PomodoroTaskVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PomodoroTaskVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pomodoro_task_fragment, parent, false)
        return PomodoroTaskVH(view)
    }

    override fun getItemCount(): Int {
        return pomodoroTaskList.size
    }

    override fun onBindViewHolder(holder: PomodoroTaskVH, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    class PomodoroTaskVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}