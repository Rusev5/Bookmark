package com.lxf.bookmark.window.main;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.lxf.bookmark.R;
import com.lxf.bookmark.window.main.adapter.UrlAdapter;
import com.lxf.bookmark.window.main.model.Url;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    private UrlAdapter urlAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        urlAdapter=new UrlAdapter(R.layout.item_url,null);

        ItemDragAndSwipeCallback mItemDragAndSwipeCallback =
                new ItemDragAndSwipeCallback(urlAdapter);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.END);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(rvMain);

        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
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

        rvMain.setLayoutManager(new LinearLayoutManager(this));
        rvMain.setAdapter(urlAdapter);
        urlAdapter.enableSwipeItem();

        urlAdapter.setNewData(setData());
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
}
