package com.lxf.bookmark.window.main;

import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.gyf.immersionbar.ImmersionBar;
import com.lxf.bookmark.Bookmark;
import com.lxf.bookmark.R;
import com.lxf.bookmark.databinding.ActivityMainBinding;
import com.lxf.bookmark.widget.PopupWindows;
import com.lxf.bookmark.window.VerificationActivity;
import com.lxf.bookmark.window.main.adapter.UrlAdapter;
import com.lxf.bookmark.window.main.model.Url;

import razerdp.basepopup.BasePopupWindow;

public class MainActivity extends AppCompatActivity implements OnItemSwipeListener {

      private MainViewModel mainViewModel;
      private UrlAdapter urlAdapter;
      private Url toBeEditedUrl;
      private String urlStr;
      private long exitTime = 0;

      private PopupWindows popupWindows;
      private Switch aSwitch;
      private EditText urlName;
      private EditText url;
      private Button btnCommmit;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this,
                    R.layout.activity_main);
            activityMainBinding.setHandlers(new MyHandlers());
            ImmersionBar.with(this).init();

            LinearLayout llStatusBar = findViewById(R.id.ll_status_bar);
            llStatusBar.setMinimumHeight(ImmersionBar.getStatusBarHeight(this));
            initPageView();
            initViewListener();
      }

      @Override
      public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
      }

      @Override
      public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
      }

      @Override
      public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
            mainViewModel.deleteWord((Url) viewHolder.itemView.getTag());
            Bookmark.showToast(MainActivity.this, "删除成功!");
      }

      @Override
      public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, boolean isCurrentlyActive) {
            canvas.drawColor(ContextCompat.getColor(MainActivity.this,
                    R.color.colorPrimaryDark));
      }

      @Override
      public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                  if ((System.currentTimeMillis() - exitTime) > 2000) {
                        Toast.makeText(getApplicationContext(), "再按一次退出程序",
                                Toast.LENGTH_SHORT).show();
                        exitTime = System.currentTimeMillis();
                  } else {
                        finish();
                  }
                  return true;
            }
            return super.onKeyDown(keyCode, event);
      }

      private void initPageView() {
            mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

            RecyclerView rvMain = findViewById(R.id.rv_main);
            aSwitch = findViewById(R.id.swch_main_edit);
            urlAdapter = new UrlAdapter(R.layout.item_url, null);
            urlAdapter.setEmptyView(R.layout.include_empty_view,
                    (ViewGroup) rvMain.getParent());

            ItemDragAndSwipeCallback mItemDragAndSwipeCallback =
                    new ItemDragAndSwipeCallback(urlAdapter);
            mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.END);
            ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
            mItemTouchHelper.attachToRecyclerView(rvMain);

            rvMain.setLayoutManager(new LinearLayoutManager(this));
            rvMain.setAdapter(urlAdapter);

            popupWindows = new PopupWindows(this);
            popupWindows.setAdjustInputMethod(false);
            url = popupWindows.getContentView().findViewById(R.id.edt_main_url);
            urlName = popupWindows.getContentView().findViewById(R.id.edt_main_url_name);
            btnCommmit = popupWindows.getContentView().findViewById(R.id.btn_main_commit);
      }

      public void initViewListener() {
            urlAdapter.setOnItemSwipeListener(this);
            urlAdapter.setOnItemClickListener((adapter, view, position) -> {
                  if (aSwitch.isChecked()) {
                        popupWindows.showPopupWindow(R.id.rv_main);
                        toBeEditedUrl = ((Url) adapter.getData().get(position));
                        urlName.setText((toBeEditedUrl.getName()));
                        url.setText(toBeEditedUrl.getUrl());
                  }
            });
            urlAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                  if (view.getId() == R.id.btn_main_it_go) {
                        urlStr = (String) view.getTag();
                        openWebSide(urlStr);
                  }
            });
            popupWindows.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
                  @Override
                  public void onDismiss() {
                        toBeEditedUrl = null;
                        urlName.setText("");
                        url.setText("");
                  }
            });
            btnCommmit.setOnClickListener(v -> {
                  if (urlName.length() > 0 && url.length() > 0) {
                        if (toBeEditedUrl != null) {
                              toBeEditedUrl.setName(String.valueOf(urlName.getText()));
                              toBeEditedUrl.setUrl(String.valueOf(url.getText()));
                              mainViewModel.updateWord(toBeEditedUrl);
                              Bookmark.showToast(MainActivity.this, "修改成功!");

                        } else {
                              mainViewModel.insertWord(new Url(String.valueOf(urlName.getText()),
                                      String.valueOf(url.getText())));
                              Bookmark.showToast(MainActivity.this, "添加成功!");
                        }
                        popupWindows.dismiss();
                  } else {
                        Bookmark.showToast(MainActivity.this, "请填写完整数据!");
                  }
            });
            mainViewModel.getListLiveData().observe(this, urls -> urlAdapter.setNewData(urls));
      }

      public class MyHandlers {
            public void openEditModelOnClick(View view) {
                  if ((((Switch) view)).isChecked()) {
                        urlAdapter.enableSwipeItem();
                  } else {
                        urlAdapter.disableSwipeItem();
                  }
            }

            public void addAItem() {
                  popupWindows.showPopupWindow(R.id.rv_main);
            }

            public void toChangeCode() {
                  Intent intent = new Intent(MainActivity.this, VerificationActivity.class);
                  intent.putExtra("forme_main", true);
                  startActivity(intent);
            }
      }

      private void openWebSide(String url) {
            if (url != null) {
                  try {
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                  } catch (Exception e) {
                        Bookmark.showToast(MainActivity.this, "Url格式错误");
                  }
            }
      }

}
