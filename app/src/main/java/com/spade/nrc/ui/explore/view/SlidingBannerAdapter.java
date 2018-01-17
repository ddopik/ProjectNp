package com.spade.nrc.ui.explore.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.spade.nrc.R;
import com.spade.nrc.ui.explore.model.SlideBanner;
import com.spade.nrc.utils.GlideApp;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 1/8/18.
 */

public class SlidingBannerAdapter extends PagerAdapter {
    private List<SlideBanner> slideBanners;
    private Context mContext;


    public SlidingBannerAdapter(Context context, List<SlideBanner> slideBanners) {
        mContext = context;
        this.slideBanners = slideBanners;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        SlideBanner slideBanner = slideBanners.get(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.item_sliding_banner, container,
                false);
        ImageView bannerImage = itemView.findViewById(R.id.banner_image);

        GlideApp.with(mContext).load(slideBanner.getSlideImage()).centerCrop().into(bannerImage);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public int getCount() {
        return slideBanners.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
