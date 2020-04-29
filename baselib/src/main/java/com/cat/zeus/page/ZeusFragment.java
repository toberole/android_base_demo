package com.cat.zeus.page;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cat.zeus.utils.APT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ZeusFragment extends Fragment {
    protected Activity activity;

    public static <T extends Fragment> T newInstance(@NonNull Class<? extends Fragment> clazz, Bundle args) {
        T fragment = null;
        try {
            fragment = (T) clazz.newInstance();
            if (null != args) {
                fragment.setArguments(args);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fragment;
    }

    public static <T extends Fragment> T newInstance(@NonNull Class<? extends Fragment> clazz) {
        return newInstance(clazz, null);
    }

    protected View setLayoutView() {
        return null;
    }

    protected int setLayout() {
        return 0;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(this.getClass().getSimpleName(), "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout = APT.getLayoutByAnnotation(this.getClass());
        if (layout == 0) {
            layout = setLayout();
        }

        View v = setLayoutView();
        if (0 != layout) {
            v = inflater.inflate(layout, container, false);
        }
        Log.i(this.getClass().getSimpleName(), "onCreateView");
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(this.getClass().getSimpleName(), "onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(this.getClass().getSimpleName(), "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(this.getClass().getSimpleName(), "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(this.getClass().getSimpleName(), "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(this.getClass().getSimpleName(), "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(this.getClass().getSimpleName(), "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(this.getClass().getSimpleName(), "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(this.getClass().getSimpleName(), "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(this.getClass().getSimpleName(), "onDetach");
    }
}
