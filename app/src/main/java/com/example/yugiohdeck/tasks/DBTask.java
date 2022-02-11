package com.example.yugiohdeck.tasks;

import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.yugiohdeck.utils.DAOCallable;
import com.example.yugiohdeck.utils.DAOCallback;

import java.util.concurrent.Callable;

public class DBTask extends AsyncTask<Void, Void, Object> {


    DAOCallable daoCallable;
    DAOCallback daoCallback;

    public DBTask(DAOCallable daoCallable, DAOCallback daoCallback) {
        this.daoCallable = daoCallable;
        this.daoCallback = daoCallback;
    }

    @Override
    protected Object doInBackground(Void... voids) {
        return daoCallable.run();
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);

        daoCallback.onJobFinish(result);
    }
}
