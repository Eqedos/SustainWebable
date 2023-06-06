package com.example.sustainwebable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerFragmentAdapter extends FragmentStateAdapter {
    private String[] titles= new String[]{"1", "2","3"};
    public ViewPagerFragmentAdapter(@NonNull IntroActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new FragmentOne();
            case 1:
                return new FragmentTwo();
            case 2:
                return new FragmentThree();
        }
        return new FragmentOne();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
