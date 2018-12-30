package com.mohsinmonad.newsviews.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mohsinmonad.newsviews.R;
import com.mohsinmonad.newsviews.news.NewsActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeRecyclerviewAdapter extends RecyclerView.Adapter<HomeRecyclerviewAdapter.ItemHolder> {

    public interface ItemClickListener {
        void onItemClick(Source source);
    }

    private ItemClickListener listener;
    private List<Source> sourceList;
    Context context;

    public HomeRecyclerviewAdapter(ItemClickListener listener, List<Source> sourceList) {
        this.listener = listener;
        this.sourceList = sourceList;
        notifyDataSetChanged();
    }

    public void setSourceList(List<Source> sourceList) {
        this.sourceList = sourceList;
        notifyDataSetChanged();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.source_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, int position) {
        holder.view.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(sourceList.get(holder.getAdapterPosition()));
                /*Intent intent = new Intent(context, NewsActivity.class);
                intent.putExtra("CRITERIA", "SPECIFIC_NEWS");
                intent.putExtra("SOURCE_ID", sourceList.get(position).getId());
                context.startActivity(intent);*/
            }
        });
        String name = sourceList.get(position).getName();
        String description = sourceList.get(position).getDescription();
        holder.name.setText(name);
        holder.description.setText(description);
        String imageUrl = sourceList.get(position).getUrlsToLogos().getMedium();
        if (!TextUtils.isEmpty(imageUrl)){
            if (!imageUrl.toLowerCase().startsWith("http")){
                imageUrl = "http:" + imageUrl;
            }
            holder.newsImage.setImageURI(Uri.parse(imageUrl));
        }
    }

    @Override
    public int getItemCount() {
        return sourceList != null ? sourceList.size() : 0;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        private  View view;
        private SimpleDraweeView newsImage;
        private  TextView description;
        private  TextView name;

        public ItemHolder(View view) {
            super(view);
            this.view = view;
            this.newsImage = view.findViewById(R.id.list_item_icon);
            this.description = view.findViewById(R.id.list_item_sub_name);
            this.name = view.findViewById(R.id.list_item_name);
        }
    }
}
