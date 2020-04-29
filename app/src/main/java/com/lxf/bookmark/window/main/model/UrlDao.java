package com.lxf.bookmark.window.main.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UrlDao {

    @Query("SELECT * FROM url")
    LiveData<List<Url>> getAllUrl();

    @Query("SELECT * FROM URL WHERE id IN (:id)")
    List<Url> getAUrl(String[] id);

    @Insert
    void addUrl(Url... url);

    @Delete
    void removeUrl(Url... url);

    @Update
    void editUrl(Url... url);

    @Query("DELETE FROM url")
    void removeAllUrl();

}
