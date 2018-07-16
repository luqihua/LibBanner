package com.lu.banner;

import android.view.Gravity;

/**
 * author: luqihua
 * date:2018/7/14
 * description:
 **/
public class IndicatorConfig {
    public enum Type {
        Number, Dot
    }

    private int mGravity;
    private Enum<Type> mType;
    private int mNormalDotColor;
    private int mSelectDotColor;
    private int mDotSize;//小圆点的直径大小

    public IndicatorConfig(Builder builder) {
        this.mGravity = builder.gravity;
        this.mType = builder.type;
        this.mNormalDotColor = builder.normalDotColor;
        this.mSelectDotColor = builder.selectDotColor;
        this.mDotSize  = builder.dotSize;
    }

    public int getGravity() {
        return mGravity;
    }

    public Enum<Type> getType() {
        return mType;
    }

    public int getNormalDotColor() {
        return mNormalDotColor;
    }

    public int getSelectDotColor() {
        return mSelectDotColor;
    }

    public int getDotSize() {
        return mDotSize;
    }
    public static class Builder {
        Enum<Type> type = Type.Dot;
        int gravity = Gravity.CENTER;
        int normalDotColor=-1;
        int selectDotColor=-1;
        int dotSize = Constants.DOT_SIZE;

        public Builder gravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder type(Enum<Type> type) {
            this.type = type;
            return this;
        }

        public Builder setDotColor(int normalDotColor,int selectDotColor) {
            this.normalDotColor = normalDotColor;
            this.selectDotColor = selectDotColor;
            return this;
        }

        public Builder setDotSize(int size){
            this.dotSize = size;
            return  this;
        }
        public IndicatorConfig build() {
            return new IndicatorConfig(this);
        }
    }
}
