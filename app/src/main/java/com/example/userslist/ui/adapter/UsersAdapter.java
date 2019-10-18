package com.example.userslist.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.userslist.R;
import com.example.userslist.model.User;
import com.example.userslist.util.APIHelper;

import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

/**
 * Created by Tourdyiev Roman on 2019-10-18.
 */
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private APIHelper apiHelper;

    private RecyclerView parentRecyclerView;

    private List<User> list;
    private final static int VIEW_TYPE_LOADING = 0;
    private final static int VIEW_TYPE_ITEM = 1;
    private boolean hideLoadingItem = false;
    private final static int LIMIT = 10;
    private int OFFSET = 0;

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) parentRecyclerView.getLayoutManager();
                if (getItemViewType(linearLayoutManager.findLastVisibleItemPosition()) == VIEW_TYPE_LOADING) {
                    OFFSET = OFFSET + LIMIT;
                    apiHelper.getUsers(LIMIT, OFFSET);
                }
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    public UsersAdapter(List<User> list) {
        this.list = list;
        apiHelper = APIHelper.getInstance();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        this.parentRecyclerView = recyclerView;
        parentRecyclerView.setOnScrollListener(scrollListener);
        OFFSET = OFFSET + LIMIT;
        apiHelper.getUsers(LIMIT, OFFSET);
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        viewType == VIEW_TYPE_LOADING ? R.layout.item_user_loading : R.layout.item_user,
                        parent,
                        false);
        return new UsersAdapter.UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int i) {

        View itemView = holder.itemView;
        int position = holder.getAdapterPosition();
        if (list.size() == 0) {
            return;
        }

        if (getItemViewType(i) == VIEW_TYPE_ITEM) {
            User user = list.get(position);
            Glide
                    .with(itemView.getContext())
                    .load(user.getImage())
                    .into(holder.userImage);

            Log.d("user.getItems()", String.valueOf(user.getItems().size()));

            GridLayoutManager gridLayoutManager = new GridLayoutManager(itemView.getContext(), 2);
            if (user.getItems().size() % 2 != 0) {
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (position == 0) {
                            return 2;
                        } else {
                            return 1;
                        }
                    }
                });
            }
            holder.recyclerView.setLayoutManager(gridLayoutManager);
            ItemsAdapter itemsAdapter = new ItemsAdapter(user.getItems());
            holder.recyclerView.setAdapter(itemsAdapter);

        } else {

            if (hideLoadingItem) {
                itemView.setVisibility(View.GONE);
            } else {
                itemView.setVisibility(View.VISIBLE);
            }
        }

    }

    public void hideLoadingItem() {
        hideLoadingItem = true;
        notifyItemChanged(list.size());
    }

    @Override
    public int getItemCount() {

        if (list != null) {
            return list.size() + 1;
        } else {
            return 1;
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position == list.size() ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {

        protected ImageView userImage;
        protected RecyclerView recyclerView;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.user_image);
            recyclerView = itemView.findViewById(R.id.user_items);
        }
    }


}
