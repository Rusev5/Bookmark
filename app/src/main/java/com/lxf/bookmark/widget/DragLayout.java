package com.lxf.bookmark.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.customview.widget.ViewDragHelper;

public class DragLayout extends FrameLayout {
    private ViewDragHelper mDragHelper;
    private View mFront;
    private View mBack;
    private int mBackWidth;
    private int mFrontWidth;
    public static final int EDGE_RIGHT = 1 << 1;

    public DragLayout(Context context) {
        super(context);
        init();
    }

    public DragLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //参数为距离父控件的上下左右距离
        mBack.layout(mFrontWidth, 0, mFrontWidth + mBackWidth, getMeasuredHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mBackWidth = mBack.getMeasuredWidth();
        mFrontWidth = mFront.getMeasuredWidth();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mFront = getChildAt(0);
        mBack = getChildAt(1);
    }

    private void init() {

        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            //会在触碰到View的时候调用一次，松开，再触碰才会调用第二次
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {

                return true;
            }

            //left:控件的X坐标   dx:水平滑动的增量
            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {

                if (child == mFront) {
                    if (left <= -mBackWidth)
                        return -mBackWidth;
                    else if (left >= 0)
                        return 0;
                    return left;
                } else {
                    if (left >= mFrontWidth)
                        return mFrontWidth;
                    else if (left <= mFrontWidth - mBackWidth)
                        return mFrontWidth - mBackWidth;
                    return left;
                }

            }
            //top:控件的Y坐标    dy:垂直滑动的增量

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                return super.clampViewPositionVertical(child, top, dy);
            }

            //手指松开的时候调用
            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                if (mFront.getLeft() <= -mBackWidth / 2) {
                    mDragHelper.smoothSlideViewTo(mFront, -mBackWidth, mFront.getTop());
                } else if (mFront.getLeft() < 0 && mFront.getLeft() > -mBackWidth / 2) {
                    mDragHelper.smoothSlideViewTo(mFront, 0, mFront.getTop());
                }
                invalidate();
            }

            @Override
            public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
                if (changedView == mFront) {
                    mBack.offsetLeftAndRight(dx);
                } else if (changedView == mBack) {
                    mFront.offsetLeftAndRight(dx);
                }

            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return 1;
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }
        });
        mDragHelper.setEdgeTrackingEnabled(EDGE_RIGHT);


    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

}
