package com.example.simune_lee.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by Simune_Lee on 2016/3/24.
 */
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment()
    {
        return  new crimeListFragment();
    }
}
