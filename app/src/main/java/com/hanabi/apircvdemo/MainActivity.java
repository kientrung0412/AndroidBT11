package com.hanabi.apircvdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.hanabi.apircvdemo.adapter.MainPagerAdapter;
import com.hanabi.apircvdemo.models.News;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static final String EXTRA_URL_NEWS = "extra.URL.NEWS";
    private Toolbar toolbar;
    private MainPagerAdapter adapter;
    private SearchView searchView;
    private ViewPager viewPager;
    private TabLayout tabLayout;


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
        tabLayout.setupWithViewPager(viewPager);
        setSupportActionBar(toolbar);
    }

    private void setupViewPager() {
        adapter = new MainPagerAdapter(getSupportFragmentManager(), 1);
        viewPager.setCurrentItem(0);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                adapter.getSaveFragment().fillData();
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
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.getNewsFragment().getLoadingScreen().startLoadingdialog();
        adapter.getNewsFragment().fillData(query);
        searchView.clearFocus();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
