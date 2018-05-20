package br.com.luisfernandez.pomodoro.ui.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.luisfernandez.pomodoro.Pomodoro
import br.com.luisfernandez.pomodoro.R
import br.com.luisfernandez.pomodoro.ui.home.pojo.ItemList
import br.com.luisfernandez.pomodoro.ui.home.pojo.ItemListContent
import br.com.luisfernandez.pomodoro.ui.home.pojo.ItemListTitle
import java.text.SimpleDateFormat
import java.util.*

class PomodoroTaskListAdapter: RecyclerView.Adapter<PomodoroTaskListAdapter.VH>() {

    val pomodoroTaskList: ArrayList<ItemList> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return when(viewType) {
            ItemList.TYPE_CONTENT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pomodoro_task, parent, false)
                PomodoroTaskVH(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pomodoro_title, parent, false)
                TitleVH(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return pomodoroTaskList[position].contentType
    }

    override fun getItemCount(): Int {
        return pomodoroTaskList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(holder, pomodoroTaskList[position])
    }

    fun add(pomodoroTaskList: List<ItemList>) {
        this.pomodoroTaskList.addAll(pomodoroTaskList)
        this.notifyDataSetChanged()
    }

    abstract class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(holder: VH, itemList: ItemList)
    }

    class PomodoroTaskVH(itemView: View) : VH(itemView) {
        var textTime = itemView.findViewById<TextView>(R.id.textTime)
        var textStatus = itemView.findViewById<TextView>(R.id.textStatus)
        var textDate = itemView.findViewById<TextView>(R.id.textDate)

        override fun bind(holder: VH, itemList: ItemList) {
            var pomodoroTaskVH = holder as PomodoroTaskVH
            val itemListContent = itemList as ItemListContent

            pomodoroTaskVH.textTime.text = itemListContent.elapsedTime
            pomodoroTaskVH.textStatus.text = itemListContent.status
            pomodoroTaskVH.textDate.text = itemListContent.finishedDate
        }
    }

    class TitleVH(itemView: View) : VH(itemView) {
        var textTitle = itemView.findViewById<TextView>(R.id.textTitle)

        override fun bind(holder: VH, itemList: ItemList) {
            var titleVH = holder as TitleVH
            val itemListTitle = itemList as ItemListTitle

            titleVH.textTitle.text = itemListTitle.title
        }
    }
}