package com.mohsinmonad.newsviews.home;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.mohsinmonad.newsviews.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeRecyclerviewAdapter extends  RecyclerView.Adapter<HomeRecyclerviewAdapter.ItemHolder> {

    Context context;

    public interface ItemClickListener {
        void onItemClick(Source source);
    }

    private ItemClickListener listener;
    private List<Source> sourceList;

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
            }
        });

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load("http://i.newsapi.org/" + sourceList.get(position).getUrlsToLogos().getMedium())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.newsImage);

        String name = sourceList.get(position).getName();
        String description = sourceList.get(position).getDescription();
        holder.name.setText(name);
        holder.description.setText(description);

    }

    @Override
    public int getItemCount() {
        return sourceList != null ? sourceList.size() : 0;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        private  View view;
        private ImageView newsImage;
        private TextView description;
        private  TextView name;

        public ItemHolder(View view) {
            super(view);
            this.view = view;
            this.newsImage = view.findViewById(R.id.list_item_icon);
            this.description =  view.findViewById(R.id.list_item_sub_name);
            this.name = view.findViewById(R.id.list_item_name);
        }
    }
}
