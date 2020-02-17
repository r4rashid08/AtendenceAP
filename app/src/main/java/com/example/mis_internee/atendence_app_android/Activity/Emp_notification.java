package com.example.mis_internee.atendence_app_android.Activity;

import android.annotation.SuppressLint;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import com.example.mis_internee.atendence_app_android.Adapter.Fragment_emp_notification_apdapter;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.ImageView;
import androidx.appcompat.app.ActionBar;

import com.example.mis_internee.atendence_app_android.R;

import java.util.Objects;


public class Emp_notification extends AppCompatActivity {
    ImageView imageView;
    @SuppressLint("ResourceAsColor")
    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
//        startActivity(getIntent());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        imageView=(ImageView)findViewById(R.id.img);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Employee Notifications");
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
        tabLayout.addTab(tabLayout.newTab().setText("OPEN "));
        tabLayout.addTab(tabLayout.newTab().setText("ClOSED"));
//        tabLayout.addTab(tabLayout.newTab().setText("Hashtags"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.darkblue));


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final Fragment_emp_notification_apdapter adapter = new Fragment_emp_notification_apdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}