package br.com.luisfernandez.pomodoro.ui.home

import android.arch.lifecycle.ViewModelProviders
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.luisfernandez.pomodoro.Pomodoro
import br.com.luisfernandez.pomodoro.R
import br.com.luisfernandez.pomodoro.android.PomodoroService
import br.com.luisfernandez.pomodoro.android.PomodoroService.ACTION_ON_FINISH
import br.com.luisfernandez.pomodoro.android.PomodoroService.ACTION_ON_TICK
import br.com.luisfernandez.pomodoro.entity.PomodoroTask
import br.com.luisfernandez.pomodoro.ui.home.viewmodel.PomodoroTaskViewModel
import kotlinx.android.synthetic.main.fragment_pomodoro_task.*
import org.jetbrains.anko.textColor
import java.util.*


class PomodoroTaskFragment : Fragment() {

    companion object {
        fun newInstance() = PomodoroTaskFragment()
    }

    lateinit var broadcastReceiver: BroadcastReceiver

    private lateinit var viewModel: PomodoroTaskViewModel
    private var currentCount = 0L

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pomodoro_task, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PomodoroTaskViewModel::class.java)

        fabPlayStop.setOnClickListener { view: View ->
            when(view.tag) {
                "PLAY" -> {
                    startCountDownTimer()
                    setPlayingState()
                }
                else -> {
                    savePomodoroTask(Pomodoro.POMODORO_TIME_IN_MILLIS - currentCount)
                    setStoppedState()
                    PomodoroService.stop(context)
                }
            }
        }
    }

    private fun setStoppedState() {
        textTimer.textColor = resources.getColor(R.color.color_text_timer_disabled)
        textTimer.text = "25:00"
        fabPlayStop.tag = "PLAY"
        fabPlayStop.setImageResource(R.drawable.icon_play_white_24dp)
    }

    private fun savePomodoroTask(taskDuration: Long) {
        viewModel.insertPomodoroTask(
                PomodoroTask(
                        taskDuration = taskDuration,
                        finishedDateTime = Date()
                )
        )
    }

    override fun onResume() {
        super.onResume()
        initBroadcastReceiver()
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
                        val timeInText = Pomodoro.getFormattedTime(current)
                        textTimer.text = timeInText

                        currentCount = current

                        setPlayingState()
                    }
                    ACTION_ON_FINISH -> {
                        savePomodoroTask(Pomodoro.POMODORO_TIME_IN_MILLIS)
                        setStoppedState()
                    }
                }
            }
        }
        activity!!.registerReceiver(broadcastReceiver, intentFilter)
    }

    private fun setPlayingState() {
        fabPlayStop.tag = "STOP"
        fabPlayStop.setImageResource(R.drawable.icon_stop_white_24dp)
    }

    fun startCountDownTimer() {
        PomodoroService.start(context)
        textTimer.textColor = resources.getColor(R.color.color_text_timer_running)
    }

    override fun onPause() {
        super.onPause()
        activity?.unregisterReceiver(broadcastReceiver)
    }
}
