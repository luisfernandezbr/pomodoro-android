package br.com.luisfernandez.pomodoro.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.luisfernandez.pomodoro.ui.home.adapter.PomodoroTaskListAdapter
import br.com.luisfernandez.pomodoro.R
import br.com.luisfernandez.pomodoro.ui.home.pojo.ItemList
import br.com.luisfernandez.pomodoro.ui.home.viewmodel.PomodoroTaskViewModel

class PomodoroTaskListFragment : Fragment() {

    companion object {
        fun newInstance() = PomodoroTaskListFragment()
    }

    private lateinit var viewModel: PomodoroTaskViewModel
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_pomodoro_task_list, container, false)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = PomodoroTaskListAdapter()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PomodoroTaskViewModel::class.java)

        val observer = Observer<List<ItemList>> { list: List<ItemList>? ->
            showContent(list)
        }

        viewModel.loadPomodoroTaskList().observe(this, observer)
    }

    fun showContent(pomodoroTaskList: List<ItemList>?) {
        Toast.makeText(context, "asdasd", Toast.LENGTH_LONG).show()
        pomodoroTaskList?.let {
            (recyclerView.adapter as PomodoroTaskListAdapter).add(it)
        }
    }
}
