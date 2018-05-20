package br.com.luisfernandez.pomodoro.ui.home

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatTextView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.luisfernandez.pomodoro.PomodoroConfig

import br.com.luisfernandez.pomodoro.R
import br.com.luisfernandez.pomodoro.entity.PomodoroTask
import kotlinx.android.synthetic.main.fragment_pomodoro_task.*
import java.util.*
import java.util.concurrent.TimeUnit

class PomodoroTaskFragment : Fragment() {

    companion object {
        fun newInstance() = PomodoroTaskFragment()
    }

    private lateinit var viewModel: PomodoroTaskViewModel
    private var timer: CountDownTimer? = null
    private var currentCount = 0L

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pomodoro_task, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PomodoroTaskViewModel::class.java)

        buttonAddPomodoro.setOnClickListener { view: View? ->
            startCountDownTimer()
        }

        buttonStopPomodoro.setOnClickListener { view: View? ->
            timer?.cancel()
            timer = null
            textTimer.text = "01:00"
            viewModel.insertPomodoroTask(
                    PomodoroTask(
                            taskDuration = currentCount,
                            finishedDateTime = Date()
                    )
            )
        }
    }

    fun startCountDownTimer() {

        timer = object: CountDownTimer(PomodoroConfig.POMODORO_TIME_IN_MILLIS, PomodoroConfig.TIMER_COUNT_INTERVAL) {
            override fun onFinish() {
                textTimer.text = "01:00"
                viewModel.insertPomodoroTask(
                        PomodoroTask(
                                taskDuration = PomodoroConfig.POMODORO_TIME_IN_MILLIS,
                                finishedDateTime = Date()
                        )
                )
            }

            override fun onTick(milliSeconds: Long) {
                currentCount = milliSeconds
                val timeInText = String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(milliSeconds),
                        TimeUnit.MILLISECONDS.toSeconds(milliSeconds) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)))

                Log.d("", timeInText)
                textTimer.text = timeInText
            }
        }
        timer?.start()
    }

}
