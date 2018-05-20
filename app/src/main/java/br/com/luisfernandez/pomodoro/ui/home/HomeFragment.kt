package br.com.luisfernandez.pomodoro.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.luisfernandez.pomodoro.R
import br.com.luisfernandez.pomodoro.entity.PomodoroTask

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: PomodoroTaskViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.home_fragment, container, false)
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
