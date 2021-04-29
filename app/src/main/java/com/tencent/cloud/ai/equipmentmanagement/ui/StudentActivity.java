package com.tencent.cloud.ai.equipmentmanagement.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.tencent.cloud.ai.equipmentmanagement.R;
import com.tencent.cloud.ai.equipmentmanagement.fragment.TabApplyFragment;
import com.tencent.cloud.ai.equipmentmanagement.fragment.TabLendFragment;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {
    private String[] tabs = {"实验室申请", "实验室设备外借"};
    private List<Fragment> tabFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        //实验室申请
        tabFragmentList.add(TabApplyFragment.newInstance(tabs[0]));
        //实验室设备外借
        tabFragmentList.add(TabLendFragment.newInstance(tabs[1]));


        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return tabFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return tabFragmentList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabs[position];
            }
        });

        //设置TabLayout和ViewPager联动
        tabLayout.setupWithViewPager(viewPager, false);
    }
}
