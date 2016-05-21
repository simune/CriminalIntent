package com.example.simune_lee.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by Simune_Lee on 2016/4/9.
 */
public class CrimeCameraActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment()
    {
        return new CrimeCameraFragment();
    }

}
