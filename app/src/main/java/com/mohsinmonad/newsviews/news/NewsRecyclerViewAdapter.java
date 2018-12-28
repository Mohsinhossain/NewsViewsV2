package com.mohsinmonad.newsviews.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.mohsinmonad.newsviews.R;
import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NewsRecyclerViewAdapter extends  RecyclerView.Adapter<NewsRecyclerViewAdapter.ItemHolder> {

    Context context;

    public NewsRecyclerViewAdapter() {

    }

    public interface ItemClickListener {
        void onItemClick(Article article);
    }

    private static final SimpleDateFormat formatTo =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
    private static final SimpleDateFormat formatFrom =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    private ItemClickListener listener;
    private List<Article> articleList;

    public NewsRecyclerViewAdapter(ItemClickListener listener, List<Article> articleList) {
        this.listener = listener;
        this.articleList = articleList;
        notifyDataSetChanged();
    }

    public void addArticles(List<Article> articleList) {
        this.articleList.addAll(articleList);
        notifyDataSetChanged();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, int position) {
        holder.view.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(articleList.get(holder.getAdapterPosition()));
            }
        });

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load("http://i.newsapi.org/" + articleList.get(position).getImageUrl())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.newsImage);


        String title = articleList.get(position).getTitle();
        String author = articleList.get(position).getAuthor();
        String date = articleList.get(position).getPublishedAt();
        holder.author.setText(author);
        holder.title.setText(title);
        try {
            holder.date.setText(formatFrom.format(formatTo.parse(date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return articleList != null ? articleList.size() : 0;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        private View view;
        private ImageView newsImage;
        private TextView author;
        private TextView title;
        private TextView date;

        public ItemHolder(View view) {
            super(view);
            this.view = view;
            this.newsImage = view.findViewById(R.id.list_item_icon);
            this.title = view.findViewById(R.id.list_item_name);
            this.author = view.findViewById(R.id.list_item_sub_name);
            this.date = view.findViewById(R.id.list_item_date);
        }
    }
}

