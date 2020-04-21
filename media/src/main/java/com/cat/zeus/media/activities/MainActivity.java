package com.cat.zeus.media.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.cat.zeus.media.R;
import com.cat.zeus.media.fragments.BlankFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BlankFragment blankFragment = new BlankFragment();
        fragmentTransaction.add(R.id.ll_xxx, blankFragment, "ff");
        fragmentTransaction.commit();
    }
}
