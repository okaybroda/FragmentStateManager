package com.viven.fragmentstatemanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

public abstract class FragmentStateManager {
    private static final String TAG = "FragmentStateManager";
    private static final boolean DEBUG = false;

    private final FragmentManager mFragmentManager;
    ViewGroup container;

    public FragmentStateManager(ViewGroup container, FragmentManager fm) {
        mFragmentManager = fm;
        this.container = container;
    }

    /**
     * Return the Fragment associated with a specified position.
     */
    public abstract Fragment getItem(int position);

    public Fragment changeFragment(int position) {
        String tag = makeFragmentName(container.getId(), getItemId(position));
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = getItem(position);
            fragmentTransaction.add(container.getId(), fragment, tag);
        } else {
            fragmentTransaction.attach(fragment);
        }

        Fragment curFrag = mFragmentManager.getPrimaryNavigationFragment();
        if (curFrag != null) {
            fragmentTransaction.detach(curFrag);
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNowAllowingStateLoss();

        return fragment;
    }

    public void removeFragment(int position) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.remove(mFragmentManager
                .findFragmentByTag(makeFragmentName(container.getId(), getItemId(position))));
        fragmentTransaction.commitNowAllowingStateLoss();
    }

    /**
     * Return a unique identifier for the item at the given position.
     * <p>
     * <p>The default implementation returns the given position.
     * Subclasses should override this method if the positions of items can change.</p>
     *
     * @param position Position within this adapter
     * @return Unique identifier for the item at position
     */
    public long getItemId(int position) {
        return position;
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }
}
