package com.lxf.bookmark.window;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.irvingryan.VerifyCodeView;
import com.lxf.bookmark.R;
import com.lxf.bookmark.window.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerificationActivity extends AppCompatActivity {
    private static final String CODE = "3336";
    @BindView(R.id.vcv_vc)
    VerifyCodeView vcvVc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        ButterKnife.bind(this);

        initListener();
    }

    private void initListener() {
        vcvVc.setListener(new VerifyCodeView.OnTextChangListener() {
            @Override
            public void afterTextChanged(String text) {
                if (text.length() == 4) {
                    if (text.equals(CODE)) {
                        Intent intent=new Intent(VerificationActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(VerificationActivity.this, "╯3╰", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                vcvVc.setText("");
                            }
                        }, 800);
                    }
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
