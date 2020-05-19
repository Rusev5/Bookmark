package com.lxf.bookmark.window.main;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.lxf.bookmark.AppDatabase;
import com.lxf.bookmark.window.main.model.Url;
import com.lxf.bookmark.window.main.model.UrlDao;

import java.util.List;

class MainRepository {
    private UrlDao urlDao;
    LiveData<List<Url>> listLiveData;

    MainRepository() {
        AppDatabase appDatabase = AppDatabase.getInstance();
        urlDao = appDatabase.getUrlDao();
        listLiveData = urlDao.getAllUrl();
    }

    void insertWord(Url... url) {
        new insertAsyncTask(urlDao).execute(url);
    }

    void updateWord(Url... url) {
        new updateAsyncTask(urlDao).execute(url);
    }

    void deleteWord(Url... url) {
        new deleteAsyncTask(urlDao).execute(url);
    }

    void deleteAllWord() {
        new deleteAllAsyncTask(urlDao).execute();
    }

    //副线程
    static class insertAsyncTask extends AsyncTask<Url, Void, Void> {
        private UrlDao urlDao;

        insertAsyncTask(UrlDao urlDao) {
            this.urlDao = urlDao;
        }

        @Override
        protected Void doInBackground(Url... url) {
            urlDao.addUrl(url);
            return null;
        }
    }

    static class deleteAsyncTask extends AsyncTask<Url, Void, Void> {
        private UrlDao urlDao;

        deleteAsyncTask(UrlDao urlDao) {
            this.urlDao = urlDao;
        }

        @Override
        protected Void doInBackground(Url... url) {
            urlDao.removeUrl(url);
            return null;
        }
    }

    static class updateAsyncTask extends AsyncTask<Url, Void, Void> {
        private UrlDao urlDao;

        updateAsyncTask(UrlDao urlDao) {
            this.urlDao = urlDao;
        }

        @Override
        protected Void doInBackground(Url... url) {
            urlDao.editUrl(url);
            return null;
        }
    }

    static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private UrlDao urlDao;

        deleteAllAsyncTask(UrlDao urlDao) {
            this.urlDao = urlDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            urlDao.removeAllUrl();
            return null;
        }
    }
}
