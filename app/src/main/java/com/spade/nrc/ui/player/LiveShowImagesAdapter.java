package com.spade.nrc.ui.player;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.spade.nrc.R;
import com.spade.nrc.utils.ChannelUtils;
import com.spade.nrc.utils.GlideApp;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 1/8/18.
 */

public class LiveShowImagesAdapter extends PagerAdapter {
    private List<String> showsImages;
    private Context mContext;


    public LiveShowImagesAdapter(List<String> showsImages, Context mContext) {
        this.showsImages = showsImages;
        this.mContext = mContext;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String imageUrl = showsImages.get(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.item_show_image, container,
                false);
        ImageView showImage = itemView.findViewById(R.id.show_image);
        RelativeLayout playerImageLayout = itemView.findViewById(R.id.player_image_layout);
//        ViewPager.LayoutParams layoutParams = (ViewPager.LayoutParams) playerImageLayout.getLayoutParams();
//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) playerImageLayout.getLayoutParams();
//        layoutParams.height = Constants.pxToDp(mContext, 280);
//        layoutParams.width = Constants.pxToDp(mContext, 280);

//        playerImageLayout.setLayoutParams(layoutParams);

        ViewCompat.setElevation(playerImageLayout, 8);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new RoundedCorners(16));

        GlideApp.with(mContext).load(imageUrl).apply(requestOptions).centerCrop()
                .placeholder(ChannelUtils.getPlayerDefault(ChannelUtils.getChannelID(position))).into(showImage);

        itemView.setTag(position);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
