package com.spade.nrc.ui.presenters.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.spade.nrc.R;
import com.spade.nrc.ui.CustomViews.CustomTextView;
import com.spade.nrc.ui.presenters.model.Presenter;
import com.spade.nrc.utils.ChannelUtils;
import com.spade.nrc.utils.GlideApp;
import com.spade.nrc.utils.PrefUtils;
import com.spade.nrc.utils.TextUtils;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 12/12/17.
 */

public class PresentersAdapter extends RecyclerView.Adapter<PresentersAdapter.PresenterViewHolder> {

    private Context context;
    private List<Presenter> presenterList;
    private int channelID;
    private OnPresenterClicked onPresenterClicked;

    public PresentersAdapter(Context context, List<Presenter> presenterList, int channelID) {
        this.context = context;
        this.presenterList = presenterList;
        this.channelID = channelID;
    }


    @Override
    public PresenterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_presenter, parent, false);
        return new PresenterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PresenterViewHolder holder, int position) {
        Presenter presenter = presenterList.get(position);
        String presenterImage = presenter.getMedia();
        String presenterName = presenter.getName();
        holder.presenterName.setText(presenterName);
        holder.showName.setText(TextUtils.getShowNames(presenter.getShowList()));
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CircleCrop());
        GlideApp.with(context).load(presenterImage).apply(requestOptions)
                .placeholder(ChannelUtils.getPresenterDefaultImage(channelID)).apply(requestOptions).into(holder.presenterImageView);

        holder.arrowImage.setImageResource(ChannelUtils.getChannelRightArrow(channelID));
         if(PrefUtils.getAppLang(context).equals(PrefUtils.ARABIC_LANG)){
            holder.arrowImage.setRotationY(180);
        }

        holder.presenterImageBackground.
                setImageResource(ChannelUtils.getPresenterImageBackground(channelID));
        holder.itemView.setOnClickListener(view -> {
            if (onPresenterClicked != null)
                onPresenterClicked.onPresenterClicked(presenter.getId());
        });
    }

    public void setOnPresenterClicked(OnPresenterClicked onPresenterClicked) {
        this.onPresenterClicked = onPresenterClicked;
    }

    public interface OnPresenterClicked {
        void onPresenterClicked(int presenterID);

    }

    @Override
    public int getItemCount() {
        return presenterList.size();
    }

    class PresenterViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView presenterName;
        private TextView showName;
        private ImageView presenterImageView, presenterImageBackground;
        private AppCompatImageView arrowImage;

        public PresenterViewHolder(View itemView) {
            super(itemView);
            presenterName = itemView.findViewById(R.id.presenter_name);
            showName = itemView.findViewById(R.id.show_title);
            presenterImageView = itemView.findViewById(R.id.presenter_image);
            arrowImage = itemView.findViewById(R.id.arrow_image);
            presenterImageBackground = itemView.findViewById(R.id.presenter_image_background);
        }
    }
}
