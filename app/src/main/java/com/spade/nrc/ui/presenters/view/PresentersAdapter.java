package com.spade.nrc.ui.presenters.view;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.spade.nrc.R;
import com.spade.nrc.ui.CustomViews.CustomTextView;
import com.spade.nrc.ui.presenters.model.Presenter;
import com.spade.nrc.utils.GlideApp;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 12/12/17.
 */

public class PresentersAdapter extends RecyclerView.Adapter<PresentersAdapter.PresenterViewHolder> {

    private Context context;
    private List<Presenter> presenterList;

    public PresentersAdapter(Context context, List<Presenter> presenterList) {
        this.context = context;
        this.presenterList = presenterList;
    }

    @Override
    public PresenterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_presenter, parent, false);
        return new PresenterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PresenterViewHolder holder, int position) {
        Presenter presenter = presenterList.get(position);
        String presenterImage = presenter.getMedia();
        String presenterName = presenter.getName();
        holder.presenterName.setText(presenterName);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
        GlideApp.with(context).load(presenterImage).into(holder.presenterImageView);

    }

    @Override
    public int getItemCount() {
        return presenterList.size();
    }

    class PresenterViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView presenterName, showName;
        private ImageView presenterImageView;
        private AppCompatImageView arrowImage;

        public PresenterViewHolder(View itemView) {
            super(itemView);
            presenterName = itemView.findViewById(R.id.presenter_name);
            showName = itemView.findViewById(R.id.show_title);
            presenterImageView = itemView.findViewById(R.id.presenter_image);
            arrowImage = itemView.findViewById(R.id.arrow_image);
        }
    }
}
