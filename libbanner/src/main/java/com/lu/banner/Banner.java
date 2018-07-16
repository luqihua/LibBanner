package com.lu.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TextureView;
import android.widget.FrameLayout;

import com.lu.libbanner.R;

import java.lang.reflect.Field;
import java.util.List;

/**
 * author: luqihua
 * date:2018/7/13
 * description:
 **/
public class Banner extends FrameLayout implements ViewPager.OnPageChangeListener {
    private Context mContext;
    //轮播的viewpager
    private ViewPager mBannerPager;
    //指标相关view
    private BannerIndicator mBannerIndicator;

    //轮播控制参数
    private int mInterval = Constants.DEFAULT_BANNER_INTERVAL;
    private int mScrollTime = Constants.DEFAULT_BANNER_SCROLL_TIME;
    private ViewPager.PageTransformer mTransformer;

    private List<?> mData;
    private BannerAdapter mBannerAdapter;
    private IBannerCreator mPageCreator;

    //定时任务
    private Runnable mTask = new Runnable() {
        @Override
        public void run() {
            mBannerPager.setCurrentItem(mBannerPager.getCurrentItem() + 1);
            postDelayed(this, mInterval);
        }
    };


    public Banner(@NonNull Context context) {
        this(context, null);
    }

    public Banner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.wiget_banner, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Banner);
        if (ta != null) {
            try {
                final String pageCreatorClass = ta.getString(R.styleable.Banner_page_creator);
                Class<IBannerCreator> c = (Class<IBannerCreator>) Class.forName(pageCreatorClass);
                mPageCreator = c.newInstance();
            } catch (Exception e) {
                throw new BannerException("you need to set a pageCreator by use app:page_creator=\"your class path\"");
            }
        }
        ta.recycle();
        initView();
    }

    private void initView() {
        mBannerPager = findViewById(R.id.view_pager);
        mBannerIndicator = findViewById(R.id.indicator);
        mBannerPager.addOnPageChangeListener(this);

        mBannerAdapter = new BannerAdapter(mContext, mPageCreator);
        mBannerPager.setAdapter(mBannerAdapter);
        initPager();
    }

    private void initPager() {
        if (mTransformer != null) {
            mBannerPager.setPageTransformer(true, mTransformer);
        }
        try {
            Field field = mBannerPager.getClass().getDeclaredField("mScroller");
            field.setAccessible(true);
            BannerScroller bannerScroller = new BannerScroller(mContext);
            bannerScroller.setDuration(mScrollTime);
            field.set(mBannerPager, bannerScroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //重写这个方法是为了解决手指滑动viewpager和定时任务控制viewpager切换冲突问题
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                start();
                break;
            default:
                stop();
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        //viewpager切换完成更新指示器
        mBannerIndicator.update(i % mData.size());
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    /*===========================对外开放方法============================*/

    /**
     * 开启轮播
     */
    public void start() {
        postDelayed(mTask, mInterval);
    }

    /**
     * 停止轮播
     */
    public void stop() {
        removeCallbacks(mTask);
    }

    /**
     * 设置控制指示器的参数
     *
     * @param config
     */
    public void setIndicatorConfig(IndicatorConfig config) {
        this.mBannerIndicator.setIndicatorConfig(config);
    }


    /**
     * 设置控制轮播的参数
     *
     * @param config
     */
    public void setBannerConfig(BannerConfig config) {
        this.mScrollTime = config.getScrollTime();
        //正确的间隔时间应该是要加上轮播切换的时间的，这样才会精确
        this.mInterval = config.getInterval() + mScrollTime;
        this.mTransformer = config.getBannerTransformer();
        initPager();
    }

    /**
     * 设置数据源
     *
     * @param data
     */
    public void setData(List<?> data) {
        if (mBannerAdapter == null) {
            throw new RuntimeException("Banner: please set an BannerPagerCreator before setData");
        }
        stop();
        this.mData = data;
        this.mBannerIndicator.init(mData.size(), 0);
        mBannerAdapter.update(data);
        mBannerPager.setCurrentItem(mData.size() * BannerAdapter.CYCLE / 2);
        start();
    }

    public void setBannerClickListener(IBannerClickListener listener) {
        mBannerAdapter.setBannerClickListener(listener);
    }
}
