package com.spade.nrc.ui.explore.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spade.nrc.R;


/**
 * Created by Ayman Abouzeid on 10/30/17.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private Context mContext;
    private TypedArray typedArray;
    private OnItemClicked onItemClicked;

    public MenuAdapter(Context mContext, TypedArray typedArray) {
        this.mContext = mContext;
        this.typedArray = typedArray;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.side_menu_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, final int position) {
        holder.menuTitle.setText(mContext.getResources().getStringArray(R.array.menu_titles)[position]);
        holder.menuTitle.setGravity(Gravity.CENTER);
        holder.menuImage.setImageResource(typedArray.getResourceId(position, 0));
        if (position != 2) {
            holder.itemView.setAlpha(1);
        } else {
            holder.itemView.setAlpha(0.6f);
        }
        holder.itemView.setOnClickListener(v -> onItemClicked.onItemClicked(position));
    }

    @Override
    public int getItemCount() {
        return mContext.getResources().getStringArray(R.array.menu_titles).length;
    }

    public void setOnItemClicked(OnItemClicked onItemClicked) {
        this.onItemClicked = onItemClicked;
    }

    public interface OnItemClicked {
        void onItemClicked(int position);
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView menuTitle;
        ImageView menuImage;

        public MenuViewHolder(View itemView) {
            super(itemView);
            menuTitle = itemView.findViewById(R.id.menu_item_title);
            menuImage = itemView.findViewById(R.id.menu_item_image);
        }
    }
}
