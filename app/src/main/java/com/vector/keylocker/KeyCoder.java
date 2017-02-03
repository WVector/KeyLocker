package com.vector.keylocker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;

/**
 * Created by Vector
 * on 2017/1/18 0018.
 */

public class KeyCoder {

    private static final String TAG = KeyCoder.class.getSimpleName();

    private Context mContext;
    private CallBack mCallBack;
    private Code mCode;

    public KeyCoder(Context context) {
        mContext = context;
    }

    public KeyCoder code(String code) {

        if (!TextUtils.isEmpty(code) && code.length() == 8) {
            mCode = new Code(code);
        } else {
            throw new IllegalArgumentException("code 不符合规则");
        }
        return this;
    }

    public KeyCoder callback(@NonNull CallBack callBack) {
        mCallBack = callBack;
        return this;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mCallBack == null || isLongPress(keyCode, event) || handleVolumeKey(keyCode);
    }

    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return mCallBack == null || handleLongVolumeKey(keyCode);
    }


    private boolean isLongPress(int keyCode, KeyEvent event) {
        if (event.getRepeatCount() == 0) {
            event.startTracking();
            return false;
        } else {
            return true;
        }
    }

    private boolean handleLongVolumeKey(int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            mCode.clear();
            mCallBack.onInput(mCode.getCode());
            VibratorUtil.vibrate(getContext(), 200L);
            return true;
        } else {
            return false;
        }
    }

    private boolean handleVolumeKey(int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            createCode(true);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            createCode(false);
            return true;
        } else {
            return false;
        }
    }

    private void createCode(boolean b) {
        mCode.append(b);
        mCallBack.onInput(mCode.getCode());
        if (mCode.isLegal()) {
            if (mCode.isEqualed()) {
                mCallBack.onVerifySuccess(mCode.getCode());
            } else {
                mCallBack.onVerifyError(mCode.getCode());
            }
        }
    }


    private Context getContext() {
        return mContext;
    }

    public interface CallBack {

        void onVerifyError(String code);

        void onVerifySuccess(String code);

        void onInput(String code);
    }
}
