package com.asv.holachat.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.asv.holachat.Fragments.callsFragment;
import com.asv.holachat.Fragments.chatsFragment;
import com.asv.holachat.Fragments.storyFragment;

public class FragmentAdapter extends FragmentPagerAdapter{
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

    switch (position) {
        case 0:
            return new chatsFragment();
//        case 1:
//            return new storyFragment();
//        case 2:
//            return new callsFragment();
        default:
            return new chatsFragment();
    }

    }

    @Override
    public int getCount() {
        return 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title = null;
        if (position == 0) {
            title = "CHAT";
        }

        //}
        return title;
    }
}
