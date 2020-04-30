package com.lxf.bookmark.window.main;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.lxf.bookmark.R;
import com.lxf.bookmark.databinding.ActivityMainBinding;
import com.lxf.bookmark.widget.PopupWindows;
import com.lxf.bookmark.window.main.adapter.UrlAdapter;
import com.lxf.bookmark.window.main.model.Url;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UrlAdapter urlAdapter;
    private PopupWindows popupWindows;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setHandlers(new MyHandlers());

        initPageView();
        initViewListener();
    }

    private void initPageView(){
        RecyclerView rvMain=findViewById(R.id.rv_main);
        urlAdapter = new UrlAdapter(R.layout.item_url, null);

        ItemDragAndSwipeCallback mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(urlAdapter);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.END);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(rvMain);

        rvMain.setLayoutManager(new LinearLayoutManager(this));
        rvMain.setAdapter(urlAdapter);
        urlAdapter.setNewData(setData());
        popupWindows= new PopupWindows(this);
        popupWindows.setAdjustInputMethod(true);
    }

    public void initViewListener(){
        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
//                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
//                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder,
                                          float dX, float dY, boolean isCurrentlyActive) {
                canvas.drawColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
            }
        };
        urlAdapter.setOnItemSwipeListener(onItemSwipeListener);
        urlAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                    popupWindows.setAdjustInputMethod(true);
                    popupWindows.showPopupWindow(R.id.rv_main);
                return false;
            }
        });
    }

    public List<Url> setData(){
        List<Url> urls=new ArrayList<>();
        int count=20;
        for(int i=0;i<count;i++){
            Url url=new Url();
            url.name="shit";
            url.url="sadfsa";
            urls.add(url);
        }
        return urls;
    }

        public class MyHandlers {
            public void openEditModelOnClick(View view) {
                if ((((Switch)view)).isChecked()) {
                    urlAdapter.enableSwipeItem();
                } else {
                    urlAdapter.disableSwipeItem();
                }
            }
        }
}
