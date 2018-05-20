package br.com.luisfernandez.pomodoro

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.luisfernandez.pomodoro.ui.home.PomodoroTaskListFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, PomodoroTaskListFragment.newInstance())
                    .commitNow()
        }

    }
}
