package com.thadocizn.empublite;

import android.app.Activity;
import android.app.Fragment;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

public class ContentsAdapter extends FragmentStatePagerAdapter {

    final BookContents contents;

    @Override
    public Fragment getItem(int position) {

        String path = contents.getChapterFile(position);

        return (SimpleContentFragment.newInstance("file:///android_asset/book/" + path));
    }

    public ContentsAdapter(Activity context, BookContents contents) {
        super(context.getFragmentManager());
        this.contents = contents;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (contents.getChapterTitle(position));
    }

    @Override
    public int getCount() {
        return contents.getChapterCount();
    }
}
