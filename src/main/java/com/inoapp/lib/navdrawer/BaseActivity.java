package com.inoapp.lib.navdrawer;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Created by tvo on 5/7/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();

//    protected ProgressDialog loadingDialog;
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        if (mToolbar != null)
            setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //  ...this is generic activity, set Up/Back button on the left
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setToolbarTitle(String title) {
        if(mToolbar!=null) {
            getSupportActionBar().setTitle(title);
        }
    }
/*
    public void showLoadingDialog() {
        if(loadingDialog==null) {
            loadingDialog = ProgressDialog.show(this, "", getString(R.string.loading), true, false);
        }
        else {
            if(!loadingDialog.isShowing()) {
                loadingDialog.show();
            }
        }
    }

    public void hideLoadingDialog() {
        if (loadingDialog!=null && loadingDialog.isShowing()) {
            try {
                loadingDialog.dismiss();
            } catch (IllegalArgumentException ignored) {}
        }
    }*/
}