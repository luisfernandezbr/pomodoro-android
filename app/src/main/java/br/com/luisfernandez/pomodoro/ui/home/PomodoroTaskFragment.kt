package br.com.luisfernandez.pomodoro.ui.home

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.com.luisfernandez.pomodoro.R
import br.com.luisfernandez.pomodoro.entity.PomodoroTask
import kotlinx.android.synthetic.main.pomodoro_task_fragment.*
import java.util.*

class PomodoroTaskFragment : Fragment() {

    companion object {
        fun newInstance() = PomodoroTaskFragment()
    }

    private lateinit var viewModel: PomodoroTaskViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.pomodoro_task_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PomodoroTaskViewModel::class.java)



        buttonAddPomodoro.setOnClickListener { view: View? ->
            viewModel.insertPomodoroTask(
                    PomodoroTask(
                            taskDuration = (Random().nextInt(55000 - 2000) + 55000).toLong(),
                            finishedDateTime = Date().toString()
                    )
            )
        }
    }

}
