package com.inoapp.lib.navdrawer;

import java.util.List;

public interface NavigationDrawerCallbacks {
    /**
     * Only called if no fragment in NavigationItem is provided
     */
    void onNavigationDrawerItemSelected(int position, NavigationItem item);

    List<NavigationItem> getMenu();
}
