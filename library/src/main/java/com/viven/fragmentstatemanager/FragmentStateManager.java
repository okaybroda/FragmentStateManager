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

    /**
     * Shows fragment at position and detaches previous fragment if exists. If fragment is found in
     * fragment manager, it is reattached else added.
     *
     * @param position
     * @return fragment at position
     */
    public Fragment changeFragment(int position) {
        String tag = makeFragmentName(container.getId(), getItemId(position));
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        /*
          If fragment manager doesn't have an instance of the fragment, get an instance
          and add it to the transaction. Else, attach the instance to transaction.
         */
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = getItem(position);
            fragmentTransaction.add(container.getId(), fragment, tag);
        } else {
            fragmentTransaction.show(fragment);
        }

        // Detach existing primary fragment
        Fragment curFrag = mFragmentManager.getPrimaryNavigationFragment();
        if (curFrag != null) {
            fragmentTransaction.hide(curFrag);
        }

        // Set fragment as primary navigator for child manager back stack to be handled by system
        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNowAllowingStateLoss();

        return fragment;
    }

    /**
     * Removes Fragment from Fragment Manager and clears all saved states. Call to changeFragment()
     * will restart fragment from fresh state.
     *
     * @param position
     */
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
