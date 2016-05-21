package com.example.simune_lee.criminalintent;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Simune_Lee on 2016/3/22.
 */
public class CrimeLab {

    private ArrayList<Crime> mCrimes;
    private CriminalIntentJSONSerializer mSeriallizer;

    //!< 约定静态变量
    private static CrimeLab sCrimeLab;
    private Context mAppContext;

    private static final String TAG = "Crime";
    private static final String FILENAME = "crime.json";

    private CrimeLab(Context appContext)
    {
        mAppContext = appContext;

        mSeriallizer = new CriminalIntentJSONSerializer(mAppContext,FILENAME);

        try
        {
            mCrimes = mSeriallizer.loadCrimes();
        }catch (Exception e)
        {
            mCrimes = new ArrayList<Crime>();
            Log.e(TAG,"Error loading crimes :",e);
        }
    }

    public static CrimeLab get(Context c)
    {
        if (sCrimeLab == null)
        {
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }

        return sCrimeLab;
    }

    public  ArrayList<Crime> getCrimes()
    {
        return  mCrimes;
    }

    public Crime getCrime(UUID uid)
    {
        for (Crime c : mCrimes)
        {
            if (c.getUUID().equals(uid))
            {
                return  c;
            }

        }
        return  null;
    }

    public void addCrime(Crime c)
    {
        mCrimes.add(c);
    }

    public void deleteCrime(Crime c)
    {
        mCrimes.remove(c);
    }

    public boolean saveCrimes()
    {
        try{
            mSeriallizer.saveCrimes(mCrimes);
            Log.d(TAG,"crimes saved to file");
            return true;
        }
        catch (Exception e)
        {
            Log.e(TAG,"Error saving crimes",e);
            return false;
        }
    }
}
