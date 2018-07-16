package com.lu.banner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * author: luqihua
 * date:2018/7/13
 * description:
 **/
public class DotIndicator extends LinearLayout {


    private Context mContext;

    private int mCount = -1;//总的数量
    private int mIndex = -1;//选中的下表

    private Drawable mNormalDotColor;
    private Drawable mSelectDotColor;

    public DotIndicator(Context context) {
        this(context, null);
    }

    public DotIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        this.mNormalDotColor = createBackground(Constants.DEFAULT_DOT_COLOR);
        this.mSelectDotColor = createBackground(Constants.DEFAULT_DOT_SELECT_COLOR);
    }

    /**
     * 设置圆点的颜色
     *
     * @param normalColor 正常圆点的颜色
     * @param selectColor 选中圆点的颜色
     */
    public void setDotColor(int normalColor, int selectColor) {
        this.mNormalDotColor = createBackground(normalColor);
        this.mSelectDotColor = createBackground(selectColor);
        if (mCount != -1) {
            init(mCount, mIndex);
        }
    }

    /**
     * 初始化指示器
     *
     * @param count 指示器总的数量
     * @param index 当前选中的下表
     */
    public void init(int count, int index) {
        this.mCount = count;
        this.mIndex = index;
        removeAllViews();
        for (int i = 0; i < mCount; i++) {
            View view = createDot(mContext);
            if (i == mIndex) {
                view.setBackground(mSelectDotColor);
            } else {
                view.setBackground(mNormalDotColor);
            }
            addView(view, i);
        }
    }

    /**
     * 更新选中的圆点
     *
     * @param index 选中的圆点下标
     */
    public void update(int index) {
        if (mIndex == index) return;
        getChildAt(mIndex).setBackground(mNormalDotColor);
        this.mIndex = index;
        getChildAt(mIndex).setBackground(mSelectDotColor);
    }


    private View createDot(Context context) {
        View view = new View(context);
        LayoutParams params = new LayoutParams(Constants.DOT_SIZE, Constants.DOT_SIZE);
        params.leftMargin = Constants.DOT_SIZE / 2;
        view.setLayoutParams(params);
        return view;
    }

    private Drawable createBackground(int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(color);
        return drawable;
    }
}
