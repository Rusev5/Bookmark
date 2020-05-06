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

//    @Override
//    protected Animator onCreateShowAnimator() {
//        return createAnimator(true);
//    }
//
//    @Override
//    protected Animator onCreateDismissAnimator() {
//        return createAnimator(false);
//    }

//    private Animator createAnimator(boolean isShow) {
//        ObjectAnimator showAnimator = ObjectAnimator.ofFloat(getDisplayAnimateView(),
//                View.TRANSLATION_Y,
//                isShow ? getHeight() * 0.75f : 0,
//                isShow ? 0 : getHeight() * 0.75f);
//        showAnimator.setDuration(1000);
//        showAnimator.setInterpolator(new OvershootInterpolator(isShow ? 3 : -3));
//        return showAnimator;
//    }

}
