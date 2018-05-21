package br.com.luisfernandez.pomodoro.ui.home

import android.arch.lifecycle.ViewModelProviders
import android.content.BroadcastReceiver
import android.content.Context
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
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.textColor
import java.util.*
import android.content.Intent
import android.content.IntentFilter
import br.com.luisfernandez.pomodoro.android.PomodoroService
import br.com.luisfernandez.pomodoro.android.PomodoroService.ACTION_ON_FINISH
import br.com.luisfernandez.pomodoro.android.PomodoroService.ACTION_ON_TICK


class PomodoroTaskFragment : Fragment() {

    companion object {
        fun newInstance() = PomodoroTaskFragment()
    }

    lateinit var broadcastReceiver: BroadcastReceiver

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

        initBroadcastReceiver()

        fabPlayStop.setOnClickListener { view: View ->
            when(view.tag) {
                "PLAY" -> {
                    startCountDownTimer()
                    fabPlayStop.tag = "STOP"
                    fabPlayStop.setImageResource(R.drawable.icon_stop_white_24dp)
                }
                else -> {
                    timer?.cancel()
                    timer = null
                    textTimer.text = "01:00"
                    textTimer.textColor = resources.getColor(R.color.color_text_timer_disabled)
                    viewModel.insertPomodoroTask(
                            PomodoroTask(
                                    taskDuration = Pomodoro.POMODORO_TIME_IN_MILLIS - currentCount,
                                    finishedDateTime = Date()
                            )
                    )
                    fabPlayStop.tag = "PLAY"
                    fabPlayStop.setImageResource(R.drawable.icon_play_white_24dp)
                }
            }
        }
    }

    private fun initBroadcastReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(ACTION_ON_TICK)
        intentFilter.addAction(ACTION_ON_FINISH)

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                when (intent.action) {
                    ACTION_ON_TICK -> {
                        val current = intent.getLongExtra("current_time", 0)
                        currentCount = current
                        val timeInText = Pomodoro.getFormattedTime(current)

                        Log.d("", timeInText)
                        textTimer.text = timeInText
                    }
                    ACTION_ON_FINISH -> {
                        textTimer.text = "01:00"
                        textTimer.textColor = resources.getColor(R.color.color_text_timer_disabled)
                        viewModel.insertPomodoroTask(
                                PomodoroTask(
                                        taskDuration = Pomodoro.POMODORO_TIME_IN_MILLIS,
                                        finishedDateTime = Date()
                                )
                        )
                        fabPlayStop.tag = "PLAY"
                        fabPlayStop.setImageResource(R.drawable.icon_play_white_24dp)
                    }
                }
            }
        }
        activity!!.registerReceiver(broadcastReceiver, intentFilter)
    }

    fun startCountDownTimer() {
        val intent = Intent(activity, PomodoroService::class.java)
        activity!!.startService(intent)
        textTimer.textColor = resources.getColor(R.color.color_text_timer_running)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (timer != null) {
            timer!!.cancel()
        }
    }
}
