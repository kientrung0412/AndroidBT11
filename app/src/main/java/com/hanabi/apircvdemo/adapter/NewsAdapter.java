package com.hanabi.apircvdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hanabi.apircvdemo.R;
import com.hanabi.apircvdemo.models.News;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHoler> {

    private LayoutInflater inflater;
    private ArrayList<News> data;
    private NewsItemOnClickListener listener;

    public NewsAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void setData(ArrayList<News> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setListener(NewsItemOnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.news_item, parent, false);
        return new NewsHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHoler holder, int position) {
        News news = data.get(position);
        holder.bindView(news);
        if (listener != null){
            holder.itemView.setOnClickListener(view -> listener.itemOnClick(news));
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class NewsHoler extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvDes, tvTime;
        private CircleImageView circleImageView;

        public NewsHoler(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDes = itemView.findViewById(R.id.tv_des);
            tvTime = itemView.findViewById(R.id.tv_time);
            circleImageView = itemView.findViewById(R.id.civ_image);
        }

        private void bindView(News news) {
            tvTitle.setText(news.getTitle());
            tvDes.setText(news.getDescription());
            tvTime.setText(news.getPublishedAt());
            Glide.with(circleImageView).load(news.getImageUrl()).into(circleImageView);
        }

    }

    public interface NewsItemOnClickListener {
        void itemOnClick(News news);
    }
}
