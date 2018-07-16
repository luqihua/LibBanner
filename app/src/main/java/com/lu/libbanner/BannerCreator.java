package com.lu.libbanner;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lu.banner.IBannerCreator;

/**
 * author: luqihua
 * date:2018/7/16
 * description:
 **/


public class BannerCreator implements IBannerCreator {
    @Override
    public ImageView createBanner(Context context) {
        return new ImageView(context);
    }

    @Override
    public void showBanner(Context context, ImageView imageView, Object url) {
        Glide.with(context).load(url).into(imageView);
    }
}
