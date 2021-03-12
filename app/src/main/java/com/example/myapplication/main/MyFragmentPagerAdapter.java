package com.example.myapplication.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class MyFragmentPagerAdapter extends FragmentStateAdapter {

    List<Fragment> mFragments;

    public MyFragmentPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Fragment> mFragments) {
        super(fragmentManager, lifecycle);
        this.mFragments=mFragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }



    @Override
    public int getItemCount() {
        return mFragments.size();
    }




}

