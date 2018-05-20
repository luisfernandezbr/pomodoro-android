package br.com.luisfernandez.pomodoro.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.luisfernandez.pomodoro.R
import br.com.luisfernandez.pomodoro.entity.PomodoroTask
import kotlinx.android.synthetic.main.pomodoro_task_list_fragment.*

class PomodoroTaskListFragment : Fragment() {

    companion object {
        fun newInstance() = PomodoroTaskListFragment()
    }

    private lateinit var viewModel: PomodoroTaskViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.pomodoro_task_list_fragment, container, false)

        val layoutManager = LinearLayoutManager(context)

        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PomodoroTaskViewModel::class.java)

        val observer = Observer<List<PomodoroTask>> { t: List<PomodoroTask>? ->
            showContent(t)
        }

        viewModel.loadPomodoroTaskList().observe(this, observer)
    }

    fun showContent(pomodoroTaskList: List<PomodoroTask>?) {
        Toast.makeText(context, "asdasd", Toast.LENGTH_LONG).show()
    }
}