package com.hanabi.apircvdemo.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hanabi.apircvdemo.NewsFragment;
import com.hanabi.apircvdemo.SaveFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private NewsFragment newsFragment = new NewsFragment();
    private SaveFragment saveFragment = new SaveFragment();

    public NewsFragment getNewsFragment() {
        return newsFragment;
    }

    public SaveFragment getSaveFragment() {
        return saveFragment;
    }

    public MainPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
            default:
                return newsFragment;
            case 1:
                return saveFragment;
        }
    }

    @Override
    public int getCount() {
        //số tab hiển thị
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
            default:
                return "Tin mới";
            case 1:
                return "Tin đã lưu";
        }
    }
}
