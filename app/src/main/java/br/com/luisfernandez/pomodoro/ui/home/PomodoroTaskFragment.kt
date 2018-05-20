package br.com.luisfernandez.pomodoro.ui.home

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.luisfernandez.pomodoro.Pomodoro

import br.com.luisfernandez.pomodoro.R
import br.com.luisfernandez.pomodoro.entity.PomodoroTask
import br.com.luisfernandez.pomodoro.ui.home.viewmodel.PomodoroTaskViewModel
import kotlinx.android.synthetic.main.fragment_pomodoro_task.*
import org.jetbrains.anko.textColor
import java.util.*

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
            textTimer.textColor = resources.getColor(R.color.color_text_timer_disabled)
            viewModel.insertPomodoroTask(
                    PomodoroTask(
                            taskDuration = currentCount,
                            finishedDateTime = Date()
                    )
            )
        }
    }

    fun startCountDownTimer() {

        timer = object: CountDownTimer(Pomodoro.POMODORO_TIME_IN_MILLIS, Pomodoro.TIMER_COUNT_INTERVAL) {
            override fun onFinish() {
                textTimer.text = "01:00"
                textTimer.textColor = resources.getColor(R.color.color_text_timer_disabled)
                viewModel.insertPomodoroTask(
                        PomodoroTask(
                                taskDuration = Pomodoro.POMODORO_TIME_IN_MILLIS,
                                finishedDateTime = Date()
                        )
                )
            }

            override fun onTick(milliSeconds: Long) {
                currentCount = milliSeconds
                val timeInText = Pomodoro.getFormattedTime(milliSeconds)

                Log.d("", timeInText)
                textTimer.text = timeInText
            }
        }
        timer?.start()
        textTimer.textColor = resources.getColor(R.color.color_text_timer_running)
    }

}
