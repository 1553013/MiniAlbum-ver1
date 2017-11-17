package com.example.huynhxuankhanh.minialbum.gallary;

import android.content.Context;
import android.util.AttributeSet;
import android.support.annotation.Nullable;

/**
 * Created by HUYNHXUANKHANH on 11/2/2017.
 */
// default design for image view: square style
// design pattern support for ViewHolder
public class SquareImageView extends android.support.v7.widget.AppCompatImageView {

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}