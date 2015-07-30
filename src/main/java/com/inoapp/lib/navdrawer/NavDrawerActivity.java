package com.inoapp.lib.navdrawer;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Base activity class for Nav Drawer. Override this to customize.
 *
 * There are 2 methods to provide fragments for Nav Drawer: pre-creation and on-demand.
 * - Pre-creation: create all during initialization. See getMenu.
 * - On-demand: create only when selected. See onNavigationDrawerItemSelected.
 */
public class NavDrawerActivity extends BaseActivity
        implements NavigationDrawerCallbacks, FragmentManager.OnBackStackChangedListener, View.OnClickListener {

    /**
     * Override this to provide menu to Nav Drawer.
     * To implement pre-creation, set NavigationItem.mFragment for each item.
     *
     * @return List<NavigationItem>
     */
    public List<NavigationItem> getMenu() {
        return null;
        /** example:
         List<NavigationItem> items = new ArrayList<NavigationItem>();
         NavigationItem item = new NavigationItem("item 1", getResources().getDrawable(R.drawable.ic_menu_check));
         item.mFragment = MyFragment.newInstance();
         items.add(item);
         return items; */
    }
    /**
     * Override this to get selection callback.
     * Important: called if NavigationItem.mFragment is not pre-created.
     * To implement on-demand, set NavigationItem.mFragment.
     *
     * @param position
     * @param item
     */
    @Override
    public void onNavigationDrawerItemSelected(int position, NavigationItem item) {
        /** example:
        item.mFragment = FragmentFactory.getInstance(position);*/
    }






    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    protected NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Override this to provide custom stuffs
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        //  ...this is root activity, no Up button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer
        //mNavigationDrawerFragment.setUserData("John Doe", "johndoe@doe.com", BitmapFactory.decodeResource(getResources(), R.drawable.avatar));

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        mToolbar.setNavigationOnClickListener(this);
    }

    /**
     * util function: convert string array (from res) to List<NavigationItem>
     * @param resIdStringArray
     * @return
     */
    public ArrayList<NavigationItem> getMenu(int resIdStringArray) {
        String[] titleArray = getResources().getStringArray(resIdStringArray);
        if (titleArray == null)
            return null;
        ArrayList<NavigationItem> items = new ArrayList<NavigationItem>();
        for (String str : titleArray) {
            items.add(new NavigationItem(str, null));
        }
        return items;
    }


    public final static int minBackStack = 1;

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
            return;
        }

        if (minBackStack > 0) {
            if (popBackStack()) return;
            supportFinishAfterTransition();
            return;
        }

        super.onBackPressed();
    }

    //  ...manual handling of nav button
    @Override
    public void onClick(View v) {
        //Log.d(getClass().getSimpleName(), "onClickNavButton");

        if (mNavigationDrawerFragment.isDrawerOpen()) mNavigationDrawerFragment.closeDrawer();
        else if (popBackStack()) return; //return after so you don't call syncState();
        else mNavigationDrawerFragment.openDrawer();
        //getActionBarDrawerToggle().syncState();
    }

    boolean popBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > minBackStack) {
            fm.popBackStack();
            return true;
        }
        return false;
    }


    @Override
    public void onBackStackChanged() {
        FragmentManager fm = getSupportFragmentManager();
        int backStackEntryCount = fm.getBackStackEntryCount();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(backStackEntryCount > minBackStack);

        //if (backStackEntryCount <= minBackStack)
            mNavigationDrawerFragment.getActionBarDrawerToggle().syncState();

        animateActionBarIcon(backStackEntryCount > minBackStack);

        if (backStackEntryCount == 0) {
            return;
        }

        //  ...getActiveFragment to set title
        FragmentManager.BackStackEntry entry = fm.getBackStackEntryAt(backStackEntryCount - 1);
        String tag = entry.getName();
        Log.d(TAG, "onBackStackChanged: count="+backStackEntryCount+", current=" + tag);
        Fragment active = fm.findFragmentByTag(tag);
        if ( active != null ) {
            actionBar.setTitle(active.getTag());
        }
    }

    //http://stackoverflow.com/questions/26835209/appcompat-v7-toolbar-up-back-arrow-not-working
    private void animateActionBarIcon(boolean burger2Arrow) {
        float start = burger2Arrow ? 0f : 1.0f;
        float end = Math.abs(start - 1);
        ValueAnimator offsetAnimator = ValueAnimator.ofFloat(start, end);
        offsetAnimator.setDuration(300);
        offsetAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        offsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float offset = (Float) animation.getAnimatedValue();
                mNavigationDrawerFragment.getActionBarDrawerToggle().onDrawerSlide(null, offset);
            }
        });
        offsetAnimator.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            //getMenuInflater().inflate(R.menu.nav_drawer, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.d(TAG, "onOptionsItemSelected " + id);

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}