package com.vector.keylocker;

import android.util.Log;

/**
 * Created by Vector
 * on 2017/1/18 0018.
 */

public class Code {
    public static final int LEGAL_LENGTH = 8;
    private String code;
    private StringBuilder tempCode;

    public String getCode() {
        return tempCode.toString().replace("0", "下").replace("1", "上");
    }


    public Code(String code) {
        this.code = code;
        tempCode = new StringBuilder();
    }

    public void clear() {
        if (tempCode != null) {
            tempCode.delete(0, tempCode.length());
        }
    }

    public synchronized void append(boolean flag) {
        if (tempCode.length() >= 8) {
            clear();
        }

        if (flag) {
            tempCode.append("1");
        } else {
            tempCode.append("0");
        }

        if (BuildConfig.DEBUG) Log.d("Code", "tempCode:" + tempCode);

    }

    public boolean isLegal(){
        return tempCode.length() == LEGAL_LENGTH;
    }

    public boolean isEqualed() {
        return  code.equals(tempCode.toString());
    }

}
