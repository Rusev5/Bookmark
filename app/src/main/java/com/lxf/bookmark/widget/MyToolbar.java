package com.lxf.bookmark.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.lxf.bookmark.R;

public class MyToolbar extends Toolbar {
    public MyToolbar(Context context) {
        super(context);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        setBackgroundResource(R.color.colorPrimaryDark);
    }
}
