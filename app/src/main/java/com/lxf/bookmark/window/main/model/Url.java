package com.lxf.bookmark.window.main.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "url")
public class Url {

    @PrimaryKey(autoGenerate = true) // 设置主键
    @ColumnInfo
    protected int id;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String url;

    public Url(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
