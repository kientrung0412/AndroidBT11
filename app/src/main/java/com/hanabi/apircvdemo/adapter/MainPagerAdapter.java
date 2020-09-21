package com.hanabi.apircvdemo.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hanabi.apircvdemo.NewsFragment;
import com.hanabi.apircvdemo.SaveFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    Fragment[] fragments;

    public MainPagerAdapter(@NonNull FragmentManager fm, Fragment... fragment) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragment;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "tin mới";
            case 1:
                return "đã lưu";
            case 2:
                return "yêu thích";
            default:
                return null;
        }
    }
}
