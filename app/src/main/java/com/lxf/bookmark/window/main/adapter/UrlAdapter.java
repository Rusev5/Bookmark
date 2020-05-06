package com.lxf.bookmark.window.main.adapter;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxf.bookmark.R;
import com.lxf.bookmark.window.main.model.Url;

import java.util.List;

public class UrlAdapter extends BaseItemDraggableAdapter<Url, BaseViewHolder> {

    public UrlAdapter(int layoutResId, List<Url> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Url data) {
        baseViewHolder.addOnClickListener(R.id.btn_main_it_go);

        baseViewHolder.itemView.setTag(data);
        baseViewHolder.itemView.findViewById(R.id.btn_main_it_go).setTag(data.getUrl());
        baseViewHolder.setText(R.id.textView, data.getName());
        baseViewHolder.setText(R.id.textView2, data.getUrl());
    }
}
