package com.xiaoge.org.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class AITipScrollView extends LinearLayout {
    public AITipScrollView(Context context) {
        this(context, null);
    }

    public AITipScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AITipScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
    }
}
