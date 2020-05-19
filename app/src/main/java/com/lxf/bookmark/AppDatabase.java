package com.lxf.bookmark;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.lxf.bookmark.window.main.model.Url;
import com.lxf.bookmark.window.main.model.UrlDao;

@Database(entities = {Url.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract UrlDao getUrlDao();

    public static AppDatabase getInstance() {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(Bookmark.getAppInstance(),
                            AppDatabase.class, "bookmark")
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);  //  数据库创建回调；
                                    Log.e("AppDatabase", "DataBase are onCreate");
                                }

                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);   //  数据库使用回调；
                                    Log.e("AppDatabase", "DataBase are onOpen");
                                }
                            })
//                            .allowMainThreadQueries()   // 数据库操作可运行在主线程
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
