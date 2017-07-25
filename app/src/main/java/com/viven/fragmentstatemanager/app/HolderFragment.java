package com.viven.fragmentstatemanager.app;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class HolderFragment extends Fragment implements Navigation {
    private final static String TAG = HolderFragment.class.getName();

    FrameLayout holderFrame;


    public HolderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_holder, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);

        holderFrame = view.findViewById(R.id.holderFrame);

        if (getChildFragmentManager().findFragmentById(R.id.holderFrame) == null) {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.holderFrame, CountingFragment.newInstance(0))
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void showNewCounter(int count) {
        Fragment fragment = CountingFragment.newInstance(count);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setEnterTransition(new Slide(Gravity.END));
        }

        getChildFragmentManager().beginTransaction()
                .addToBackStack(null)
                .setReorderingAllowed(true)
                .replace(R.id.holderFrame, fragment)
                .commitAllowingStateLoss();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }
}
