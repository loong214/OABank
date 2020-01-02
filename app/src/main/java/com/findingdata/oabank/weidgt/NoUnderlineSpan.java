package com.findingdata.oabank.weidgt;

import android.text.TextPaint;
import android.text.style.UnderlineSpan;

/**
 * Created by Loong on 2020/1/2.
 * Version: 1.0
 * Describe: 电话连接去掉下划线
 */
public class NoUnderlineSpan extends UnderlineSpan {
    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(ds.linkColor);
        ds.setUnderlineText(false);
    }
}
