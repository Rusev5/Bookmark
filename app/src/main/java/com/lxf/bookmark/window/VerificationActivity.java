package com.lxf.bookmark.window;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.irvingryan.VerifyCodeView;
import com.gyf.immersionbar.ImmersionBar;
import com.lxf.bookmark.Bookmark;
import com.lxf.bookmark.R;
import com.lxf.bookmark.window.main.MainActivity;
import com.vansuita.gaussianblur.GaussianBlur;

public class VerificationActivity extends AppCompatActivity {
      private String code;
      private SharedPreferences.Editor editor;
      private VerifyCodeView vcvVc;
      private boolean isFromeMain;
      private TextView tvTip;
      private boolean isCanSetNewCode = false;
      private String firstInputNewCode;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_verification);
            vcvVc = findViewById(R.id.vcv_vc);
            tvTip = findViewById(R.id.tv_vrfct_tip);
            ImageView imageView = findViewById(R.id.img_main);

            ImmersionBar.with(this).init();

            Bitmap blurredBitmap = GaussianBlur.with(this).render(R.drawable.bg_setting);
            imageView.setImageBitmap(blurredBitmap);

            code = Bookmark.getUserSharedPreferences().getString("code", "0000");

            initListener();
            isFromeMain = getIntent().getBooleanExtra("forme_main", false);
            if (isFromeMain) {
                  tvTip.setText(R.string.input_initial_password);
            }
      }

      private void initListener() {
            vcvVc.setListener(text -> {
                  if (isFromeMain && isCanSetNewCode) {
                        if (text.length() == 4) {
                              if (firstInputNewCode == null) {
                                    firstInputNewCode = text;
                                    tvTip.setText(R.string.input_secondary_new_password);
                                    initCodeInputView();
                              } else {
                                    if (text.equals(firstInputNewCode)) {
                                          editor = Bookmark.getUserSharedPreferences().edit();
                                          editor.putString("code", firstInputNewCode);
                                          editor.apply();
                                          finish();
                                    } else {
                                          Toast.makeText(this,
                                                  "" + getResources().getString((R.string.code_confirm_error)),
                                                  Toast.LENGTH_SHORT).show();
                                          tvTip.setText(R.string.input_new_password);
                                          firstInputNewCode = null;
                                          initCodeInputView();
                                    }
                              }
                        }
                  } else if (isFromeMain) {
                        if (text.length() == 4) {
                              if (text.equals(code)) {
                                    initCodeInputView();
                                    tvTip.setText(R.string.input_new_password);
                                    isCanSetNewCode = true;
                              } else {
                                    Toast.makeText(this, "旧密码不正确!", Toast.LENGTH_SHORT).show();
                                    initCodeInputView();
                              }
                        }
                  } else {
                        if (text.length() == 4) {
                              if (text.equals(code)) {
                                    Intent intent = new Intent(VerificationActivity.this,
                                            MainActivity.class);
                                    startActivity(intent);
                              } else {
                                    Toast.makeText(VerificationActivity.this, "╯3╰",
                                            Toast.LENGTH_SHORT).show();
                                    initCodeInputView();
                              }
                        }
                  }
            });
      }

      public void initCodeInputView() {
            new Handler().postDelayed(() -> vcvVc.setText(""), 800);
      }

      @Override
      protected void onPause() {
            super.onPause();
            finish();
      }
}
