package com.hanabi.apircvdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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
import com.hanabi.apircvdemo.api.ApiBuilder;
import com.hanabi.apircvdemo.dao.AppDatabase;
import com.hanabi.apircvdemo.models.News;
import com.hanabi.apircvdemo.models.NewsResponse;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment implements NewsAdapter.NewsItemOnClickListener, Callback<NewsResponse> {

    private static final String TAG = NewsFragment.class.getCanonicalName();
    private RecyclerView recyclerView;
    private Activity activity;
    private NewsAdapter adapter;
    private final String apiKey = "282a23bed8bb46aa8f7427d5686b819e", language = "vi";
    private LoadingScreen loadingScreen;
    private FileManager fileManager;

    private final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


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
        activity = getActivity();
        fileManager = new FileManager();
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
    public void itemOnLongClick(News news, View view) {
        showPopupMenu(news, view);
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

    private void showPopupMenu(News news, View view) {
        PopupMenu popup = new PopupMenu(activity, view);
        popup.inflate(R.menu.menu_news);
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        popup.setOnMenuItemClickListener(item -> {
            if (checkPermission(true, PERMISSIONS)) {
                String path = activity.getExternalFilesDir(null).getAbsolutePath() + "/save/";
                fileManager.download(news.getUrl(), path, new FileManager.FileDownloadCallBack() {
                    @Override
                    public void onSuccess(String path) {
                        news.setPathSave(path);
                        AppDatabase.getInstance(activity).newsDao().insert(news);
                        activity.runOnUiThread(() -> Toast.makeText(activity, "Thêm thành công", Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                });

            } else {
                Toast.makeText(activity, "Ban ko the lua khi chua cap quyen", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
        popup.show();

    }

    private boolean checkPermission(boolean withRequest, String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String p : permissions) {
                if (activity.checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED) {
                    if (withRequest) {
                        requestPermissions(permissions, 0);
                    }
                    return false;
                }

            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (checkPermission(false, PERMISSIONS)) {
            Toast.makeText(activity, "Ban ko the lua khi chua cap quyen", Toast.LENGTH_SHORT).show();
        }
    }

}
