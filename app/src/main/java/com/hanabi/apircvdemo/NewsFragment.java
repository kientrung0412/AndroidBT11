package com.hanabi.apircvdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hanabi.apircvdemo.adapter.NewsAdapter;
import com.hanabi.apircvdemo.api.ApiBuilder;
import com.hanabi.apircvdemo.models.News;
import com.hanabi.apircvdemo.models.NewsResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment implements NewsAdapter.NewsItemOnClickListener, Callback<NewsResponse> {

    private RecyclerView recyclerView;
    private Activity activity;
    private NewsAdapter adapter;
    private final String apiKey = "282a23bed8bb46aa8f7427d5686b819e", language = "vi";
    private LoadingScreen loadingScreen;

    public LoadingScreen getLoadingScreen() {
        return loadingScreen;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MainActivity) getActivity();
        loadingScreen = new LoadingScreen(activity);
        recyclerView = activity.findViewById(R.id.rc_news);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new NewsAdapter(getLayoutInflater());
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);

    }

    public void fillData(String keySearch) {
        ApiBuilder.getInstance().getNews(keySearch, apiKey, language).enqueue(this);
    }

    @Override
    public void itemOnClick(News news) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(MainActivity.EXTRA_URL_NEWS, news.getUrl());
        startActivity(intent);
    }

    @Override
    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
        ArrayList<News> news = response.body().getNews();
        adapter.setData(news);
        loadingScreen.dismissLoadingDialog();
    }

    @Override
    public void onFailure(Call<NewsResponse> call, Throwable t) {
        Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
