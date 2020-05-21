package com.lxf.bookmark.window;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.lxf.bookmark.Bookmark;
import com.lxf.bookmark.R;
import com.lxf.bookmark.databinding.ActivityVerificationBinding;
import com.lxf.bookmark.window.main.MainActivity;
import com.vansuita.gaussianblur.GaussianBlur;

public class VerificationActivity extends AppCompatActivity {
    private String code;
    private SharedPreferences.Editor editor;
    private boolean isFromeMain;
    private boolean isCanSetNewCode = false;
    private String firstInputNewCode;
    private ActivityVerificationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImmersionBar.with(this).init();
        Bitmap blurredBitmap = GaussianBlur.with(this).render(R.drawable.bg);
        binding.imgMain.setImageBitmap(blurredBitmap);

        initListener();

        code = Bookmark.getUserSharedPreferences().getString("code", "0000");
        isFromeMain = getIntent().getBooleanExtra("forme_main", false);

        if (isFromeMain) {
            binding.tvVrfctTip.setText(R.string.input_initial_password);
        }
    }

    private void initListener() {
        binding.vcvVc.setListener(text -> {
            if (isFromeMain && isCanSetNewCode) {
                if (text.length() == 4) {
                    if (firstInputNewCode == null) {
                        firstInputNewCode = text;
                        binding.tvVrfctTip.setText(R.string.input_secondary_new_password);
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
                            binding.tvVrfctTip.setText(R.string.input_new_password);
                            firstInputNewCode = null;
                            initCodeInputView();
                        }
                    }
                }
            } else if (isFromeMain) {
                if (text.length() == 4) {
                    if (text.equals(code)) {
                        initCodeInputView();
                        binding.tvVrfctTip.setText(R.string.input_new_password);
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
        new Handler().postDelayed(() -> binding.vcvVc.setText(""), 800);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
