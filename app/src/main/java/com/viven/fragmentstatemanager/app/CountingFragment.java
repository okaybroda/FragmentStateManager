package com.viven.fragmentstatemanager.app;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CountingFragment extends Fragment {

    Navigation navigation;

    int counter;

    public static CountingFragment newInstance(int count) {
        Bundle bundle = new Bundle();
        bundle.putInt("count", count);
        CountingFragment countingFragment = new CountingFragment();
        countingFragment.setArguments(bundle);
        return countingFragment;
    }


    public CountingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        counter = getArguments().getInt("count", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_counting, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView txtCounter = (TextView) view.findViewById(R.id.counter);
        txtCounter.setText(counter + "");

        view.findViewById(R.id.btnIncrease).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (navigation != null) {
                    navigation.showNewCounter(counter + 1);
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getParentFragment() != null && getParentFragment() instanceof Navigation) {
            navigation = (Navigation) getParentFragment();
        }
    }
}
