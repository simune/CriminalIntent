package com.example.simune_lee.criminalintent;

import android.graphics.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Simune_Lee on 2016/4/9.
 */
public class CrimeCameraFragment extends Fragment {

    private static final String TAG = "CrimeCametaFragment";

    private Camera mCamera;
    private SurfaceView mSurfaceView;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup parent,
                             Bundle saveInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_crime_camera,parent,false);

        Button takePictureButton = (Button)v
                .findViewById(R.id.crime_camera_takePictureButton);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mSurfaceView = (SurfaceView)v.findViewById(R.id.crime_camera_surfaceView);

        return v;
    }
}
