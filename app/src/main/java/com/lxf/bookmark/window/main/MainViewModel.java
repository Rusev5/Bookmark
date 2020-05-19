package com.lxf.bookmark.window.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.lxf.bookmark.window.main.model.Url;

import java.util.List;

public class MainViewModel extends ViewModel {

    private MainRepository repository;

    public MainViewModel() {
        repository = new MainRepository();
    }

    LiveData<List<Url>> getListLiveData() {
        return repository.listLiveData;
    }

    void insertWord(Url... url) {
        repository.insertWord(url);
    }

    void updateWord(Url... url) {
        repository.updateWord(url);
    }

    void deleteWord(Url... url) {
        repository.deleteWord(url);
    }

    void deleteAllWord() {
        repository.deleteAllWord();
    }
}
