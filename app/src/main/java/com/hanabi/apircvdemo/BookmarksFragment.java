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
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hanabi.apircvdemo.adapter.NewsAdapter;
import com.hanabi.apircvdemo.dao.AppDatabase;
import com.hanabi.apircvdemo.models.News;

import java.util.List;

public class BookmarksFragment extends Fragment implements NewsAdapter.NewsItemOnClickListener {

    private RecyclerView recyclerView;
    private Activity activity;
    private NewsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bookmarks, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        recyclerView = activity.findViewById(R.id.rc_bookmarks);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new NewsAdapter(getLayoutInflater());
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        fillData();

    }

    public void fillData() {
        List<News> news = AppDatabase.getInstance(activity).newsDao().getBookmarksList(true);
        adapter.setData(news);
    }

    @Override
    public void itemOnClick(News news) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(MainActivity.EXTRA_URL_NEWS, news.getUrl());
        startActivity(intent);
    }

    @Override
    public void itemOnLongClick(News news, View view) {
        showPopupMenu(news, view);
    }

    private void showPopupMenu(News news, View view) {
        PopupMenu popupMenu = new PopupMenu(activity, view);
        popupMenu.inflate(R.menu.menu_bookmarks);
        popupMenu.setOnMenuItemClickListener(item -> {
            AppDatabase.getInstance(activity).newsDao().delete(news);
            Toast.makeText(activity, "Xóa thành công", Toast.LENGTH_SHORT).show();
            fillData();
            return true;
        });
        popupMenu.show();
    }

}
