package br.com.luisfernandez.pomodoro;

import android.os.AsyncTask;

import java.util.List;

import br.com.luisfernandez.pomodoro.entity.PomodoroTask;

public class Test {


    public void test() {
        new AsyncTask<Void, Void, List<PomodoroTask>>() {

            @Override
            protected List<PomodoroTask> doInBackground(Void... voids) {
                return null;
            }

            @Override
            protected void onPostExecute(List<PomodoroTask> pomodoroTasks) {
                super.onPostExecute(pomodoroTasks);
            }
        };
    }

    static class MyAsyncTask extends  AsyncTask<Void, Void, List<PomodoroTask>> {
        @Override
        protected List<PomodoroTask> doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(List<PomodoroTask> pomodoroTasks) {
            super.onPostExecute(pomodoroTasks);
        }
    }
}
