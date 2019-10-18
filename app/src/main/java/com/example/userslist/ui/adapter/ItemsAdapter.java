package com.example.userslist.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.userslist.R;

import java.util.List;

/**
 * Created by Tourdyiev Roman on 2019-10-18.
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {

    private List<String> images;
    private int width;

    public ItemsAdapter(List<String> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_user_items,
                        parent,
                        false);
        width = parent.getMeasuredWidth() / 2 - parent.getContext().getResources().getDimensionPixelSize(R.dimen.small_dimen);
        return new ItemsAdapter.ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int i) {

        View itemView = holder.itemView;
        int position = holder.getAdapterPosition();
        String image = images.get(position);

        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) itemView.getLayoutParams();
        layoutParams.height = width;
        layoutParams.width = width;
        if (images.size() % 2 != 0) {
            //odd
            if (position == 0) {
                layoutParams.height = 2*width;
                layoutParams.width = 2*width;
            }
        }

        itemView.setLayoutParams(layoutParams);

        Glide
                .with(itemView.getContext())
                .load(image)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder {

        protected ImageView image;


        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
