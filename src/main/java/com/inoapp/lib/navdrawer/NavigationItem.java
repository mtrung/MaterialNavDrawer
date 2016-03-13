package com.inoapp.lib.navdrawer;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

/**
 * Created by tvo on 5/5/2015.
 */
public class NavigationItem {
    private String mText;
    private Drawable mDrawable;
    public Class mFragmentClass;

    public Fragment mFragment;


    public NavigationItem(String text, Drawable drawable) {
        mText = text;
        mDrawable = drawable;
    }
    public NavigationItem(String text, Drawable drawable, Class fragmentClass) {
        mText = text;
        mDrawable = drawable;
        mFragmentClass = fragmentClass;
    }

    public static NavigationItem newInstance(Context context, int strResId, int drawableResId, Class fragmentClass) {
        return new NavigationItem(
                context.getString(strResId),
                ContextCompat.getDrawable(context, drawableResId),
                fragmentClass);
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

    public Fragment createFragment() throws IllegalAccessException, InstantiationException {
        if (mFragmentClass == null) return null;
        if (mFragment != null) return null;

        mFragment = (Fragment) mFragmentClass.newInstance();
        return mFragment;
    }
}
