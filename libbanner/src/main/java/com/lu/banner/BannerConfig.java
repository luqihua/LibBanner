package com.lu.banner;

import android.support.v4.view.ViewPager;

/**
 * author: luqihua
 * date:2018/7/14
 * description:
 **/
public class BannerConfig {
    private int mInterval;
    private int mScrollTime;
    private ViewPager.PageTransformer mBannerTransformer;

    public BannerConfig(Builder builder) {
        this.mInterval =builder.interval;
        this.mScrollTime = builder.scrollTime;
        this.mBannerTransformer = builder.bannerTransformer;
    }

    public int getInterval() {
        return mInterval;
    }

    public int getScrollTime() {
        return mScrollTime;
    }

    public ViewPager.PageTransformer getBannerTransformer() {
        return mBannerTransformer;
    }

    public static class Builder{
       int interval;
       int scrollTime;
       ViewPager.PageTransformer bannerTransformer;

        public Builder setInterval(int interval) {
            this.interval = interval;
            return this;
        }

        public Builder setScrollTime(int scrollTime) {
            this.scrollTime = scrollTime;
            return this;
        }

        public Builder setBannerTransformer(ViewPager.PageTransformer bannerTransformer) {
            this.bannerTransformer = bannerTransformer;
            return this;
        }

        public BannerConfig build() {
            return new BannerConfig(this);
        }
    }
}
