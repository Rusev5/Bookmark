package com.lxf.bookmark.widget;

import android.content.Context;
import android.view.View;

import com.lxf.bookmark.R;

import razerdp.basepopup.BasePopupWindow;

public class PopupWindows extends BasePopupWindow {

    public PopupWindows(Context context) {
        super(context);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.layout_add_url);
    }

}
