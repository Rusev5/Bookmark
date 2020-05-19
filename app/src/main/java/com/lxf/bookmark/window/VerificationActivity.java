package com.lxf.bookmark.window;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.irvingryan.VerifyCodeView;
import com.lxf.bookmark.R;
import com.lxf.bookmark.window.main.MainActivity;


public class VerificationActivity extends AppCompatActivity {
    private static final String CODE = "3336";
    VerifyCodeView vcvVc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        vcvVc=findViewById(R.id.vcv_vc);
        initListener();
    }

    private void initListener() {
        vcvVc.setListener(text -> {
            if (text.length() == 4) {
                if (text.equals(CODE)) {
                    Intent intent=new Intent(VerificationActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(VerificationActivity.this, "╯3╰", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(() -> vcvVc.setText(""), 800);
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
