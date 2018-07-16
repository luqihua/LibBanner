package com.lu.banner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lu.libbanner.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author: luqihua
 * date:2018/7/13
 * description:
 **/
public class BannerAdapter extends PagerAdapter implements View.OnClickListener {
    //为了达到无限循环的效果(实际上只是将数量增加到900倍实现伪的无限循环，这个数字可以定义大一些)
    public static final int CYCLE = 900;
    private Context mContext;
    private List<?> mData = new ArrayList<>();
    private SparseArray<FrameLayout> images = new SparseArray<>();
    private IBannerCreator mBannerCreator;
    private IBannerClickListener mIBannerClickListener;

    public BannerAdapter(Context context, IBannerCreator creator) {
        this.mContext = context;
        this.mBannerCreator = creator;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size() * CYCLE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final int realPosition = position % mData.size();
        FrameLayout itemView= images.get(realPosition);
        if (itemView == null) {
            itemView = createPage(mContext);
            images.put(realPosition, itemView);
        }
        ImageView imageView = (ImageView) itemView.getChildAt(0);
        //使用key来设置tag，因为如果使用的是Glide框架加载图片，setTag()方法会被glide使用
        imageView.setTag(R.id.tag_banner_item, mData.get(realPosition));
        mBannerCreator.showBanner(mContext, imageView, mData.get(realPosition));
        container.addView(itemView);
        return itemView;
    }

    //创建banner 一般是ImageView
    //但如果用了fresco框架  也可能是SimpleDrawee，所以这里通过自定义的mPageCreator来获取
    private FrameLayout createPage(Context context) {
        ImageView imageView = mBannerCreator.createBanner(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setOnClickListener(this);

        //使用一个FrameLayout包装一下，这是为了解决直接添加给ViewPager后无法点击问题
        FrameLayout frameLayout = new FrameLayout(mContext);
        frameLayout.addView(imageView);
        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        frameLayout.setLayoutParams(params);
        return frameLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    //banner被点击的监听
    @Override
    public void onClick(View view) {
        Object o = view.getTag(R.id.tag_banner_item);
        if (mIBannerClickListener != null) {
            mIBannerClickListener.onClick(o);
        }
    }

    /*=================对外开放的方法==================*/

    /**
     * 更新数据源
     *
     * @param data
     */
    public void update(List<?> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    /**
     * 设置banner点击监听
     *
     * @param listener
     */
    public void setBannerClickListener(IBannerClickListener listener) {
        this.mIBannerClickListener = listener;
    }
}

