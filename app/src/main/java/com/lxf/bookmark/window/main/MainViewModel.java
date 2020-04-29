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

    void insertWord(Url... words) {
        repository.insertWord(words);
    }

    void updateWord(Url... words) {
        repository.updateWord(words);
    }

    void deleteWord(Url... words) {
        repository.deleteWord(words);
    }

    void deleteAllWord() {
        repository.deleteAllWord();
    }
}
