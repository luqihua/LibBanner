package com.lu.banner;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lu.libbanner.R;

import java.util.Locale;

/**
 * author: luqihua
 * date:2018/7/14
 * description:
 **/
public class BannerIndicator extends LinearLayout {
    private TextView tvNumberV;//数字指示器
    private DotIndicator mDotIndicatorV;//点指示器

    private Enum<IndicatorConfig.Type> mType = IndicatorConfig.Type.Dot;

    private int mCount = -1;
    private int mIndex = -1;

    public BannerIndicator(Context context) {
        super(context);
    }

    public BannerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.wiget_banner_indicator, this);
        initView();
        switchIndicator();
        setGravity(Gravity.CENTER);
    }

    private void initView() {
        tvNumberV = findViewById(R.id.indicator_number);
        mDotIndicatorV = findViewById(R.id.indicator_dot);
    }

    private void switchIndicator() {
        if (mType == IndicatorConfig.Type.Dot) {
            mDotIndicatorV.setVisibility(VISIBLE);
            tvNumberV.setVisibility(GONE);
        } else {
            tvNumberV.setVisibility(VISIBLE);
            mDotIndicatorV.setVisibility(GONE);
        }
    }

    /*======================对外公开的方法==========================*/

    /**
     * 配置指示器的样式
     * @param config
     */
    public void setIndicatorConfig(IndicatorConfig config) {
        this.mType = config.getType();
        switchIndicator();
        setGravity(config.getGravity());
        if (config.getNormalDotColor() !=-1) {
            mDotIndicatorV.setDotColor(config.getNormalDotColor(),config.getSelectDotColor());
        }
    }

    /**
     * 初始化Indicator数量
     * @param count
     * @param index
     */
    public void init(int count, int index) {
        this.mCount = count;
        this.mIndex = index;
        if (mType == IndicatorConfig.Type.Dot) {
            mDotIndicatorV.init(mCount,mIndex);
        } else {
            tvNumberV.setText(String.format(Locale.CHINA,Constants.INDICATOR_NUM_FORMAT,(mIndex+1),mCount));
        }
    }

    /**
     * 更新选中下标
     * @param index
     */
    public void update(int index) {
        this.mIndex = index;
        if (mType == IndicatorConfig.Type.Dot) {
            mDotIndicatorV.update(mIndex);
        } else {
            tvNumberV.setText(String.format(Locale.CHINA,Constants.INDICATOR_NUM_FORMAT, (mIndex+1), mCount));
        }
    }

}
