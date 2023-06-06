package com.example.sustainwebable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class IntroActivity extends AppCompatActivity {
    ViewPagerFragmentAdapter viewPagerFragmentAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    private String[] titles= new String[]{"1","2","3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        viewPager2 = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(this);
        viewPager2.setAdapter(viewPagerFragmentAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, ((tab, position) -> tab.setText(titles[position]))).attach();
    }
}