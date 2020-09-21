package com.hanabi.apircvdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hanabi.apircvdemo.adapter.MainPagerAdapter;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static final String EXTRA_URL_NEWS = "extra.URL.NEWS";

    private Toolbar toolbar;
    private MainPagerAdapter adapter;
    private SearchView searchView;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private NewsFragment newsFragment = new NewsFragment();
    private SaveFragment saveFragment = new SaveFragment();
    private BookmarksFragment bookmarksFragment = new BookmarksFragment();


    private Fragment[] fragments = new Fragment[]{newsFragment, saveFragment, bookmarksFragment};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }

    private void initViews() {
        toolbar = findViewById(R.id.tb_main);
        viewPager = findViewById(R.id.vp_main);
        tabLayout = findViewById(R.id.tl_main);

        setupViewPager();
        setSupportActionBar(toolbar);
    }

    private void setupViewPager() {
        adapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 1:
                        saveFragment.fillData();
                        break;
                    case 2:
                        bookmarksFragment.fillData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        searchView = (SearchView) menu.findItem(R.id.it_app_bar_search).getActionView();
        searchView.setQueryHint("Bạn cần tìm gì.....");
        searchView.setOnQueryTextListener(this);
        viewPager.setCurrentItem(0);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        newsFragment.getLoadingScreen().startLoadingdialog();
        newsFragment.fillData(query);
        viewPager.setCurrentItem(0);
        searchView.clearFocus();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}
