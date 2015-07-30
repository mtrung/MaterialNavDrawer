package com.inoapp.lib.navdrawer;


import android.support.v4.app.Fragment;
import android.graphics.drawable.Drawable;

/**
 * Created by tvo on 5/5/2015.
 */
public class NavigationItem {
    private String mText;
    private Drawable mDrawable;

    public Fragment mFragment;

    public NavigationItem(String text, Drawable drawable) {
        mText = text;
        mDrawable = drawable;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }
}
