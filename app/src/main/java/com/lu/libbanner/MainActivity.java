package com.lu.libbanner;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

import com.lu.banner.Banner;
import com.lu.banner.BannerConfig;
import com.lu.banner.IBannerClickListener;
import com.lu.banner.IndicatorConfig;
import com.lu.banner.transforms.ScaleInOutTransformer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Banner mBanner;
    private List<String> mData = new ArrayList<>();

    {
        mData.add("http://p83nf214c.bkt.clouddn.com/1525416286.jpg");
        mData.add("http://p83nf214c.bkt.clouddn.com/1525419629.jpg");
        mData.add("http://p83nf214c.bkt.clouddn.com/1525416286.jpg");
        mData.add("http://p83nf214c.bkt.clouddn.com/1525419629.jpg");
        mData.add("http://p83nf214c.bkt.clouddn.com/1525416286.jpg");
        mData.add("http://p83nf214c.bkt.clouddn.com/1525419629.jpg");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBanner = findViewById(R.id.banner);


        BannerConfig bannerConfig = new BannerConfig.Builder()
                .setInterval(2000)
                .setScrollTime(1000)
                .setBannerTransformer(new ScaleInOutTransformer())
                .build();

        IndicatorConfig indicatorConfig = new IndicatorConfig.Builder()
                .gravity(Gravity.RIGHT)
                .type(IndicatorConfig.Type.Number)
                .build();

//        mBanner.setBannerConfig(bannerConfig);
//        mBanner.setIndicatorConfig(indicatorConfig);
        mBanner.setBannerClickListener(new IBannerClickListener() {
            @Override
            public void onClick(Object o) {
                Toast.makeText(MainActivity.this, "o:" + o, Toast.LENGTH_SHORT).show();
            }
        });
        mBanner.setData(mData);
    }
}
