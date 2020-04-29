package com.lxf.bookmark.window.main.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "url")
public class Url {

    @PrimaryKey(autoGenerate = true) // 设置主键
    @ColumnInfo
    public int id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String url;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
