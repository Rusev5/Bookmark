package com.lxf.bookmark.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

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

    @Override
    protected Animator onCreateShowAnimator() {
        return createAnimator();
    }

    @Override
    protected Animator onCreateDismissAnimator() {
        return createAnimator();
    }

    private Animator createAnimator() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(getDisplayAnimateView(), "alpha", 0f, 1f);
        objectAnimator.setDuration(500);
        return objectAnimator;
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        Animation showAnimation = new AlphaAnimation(1f, 0f);
        showAnimation.setDuration(300);
        return showAnimation;
    }

}
