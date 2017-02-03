package com.vector.keylocker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String code = "11110000";

    private TextView mTextView;
    private KeyCoder mKeyCoder;
    private TextView mTipTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.tv_result);
        mTipTv = (TextView) findViewById(R.id.tv_tips);

        mKeyCoder = new KeyCoder(this)
                .code(code)
                .callback(new KeyCoder.CallBack() {
                    @Override
                    public void onVerifyError(String code) {
                        VibratorUtil.vibratePattern(MainActivity.this);
                        mTextView.setText(String.format("密码错误 : %s", code));
                    }

                    @Override
                    public void onVerifySuccess(String code) {
                        VibratorUtil.vibrateDot(MainActivity.this);
                        mTextView.setText(String.format("密码正确 : %s", code));
                    }

                    @Override
                    public void onInput(String code) {

                        mTipTv.setText(code);

                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mKeyCoder.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return mKeyCoder.onKeyLongPress(keyCode, event) || super.onKeyLongPress(keyCode, event);
    }


}
