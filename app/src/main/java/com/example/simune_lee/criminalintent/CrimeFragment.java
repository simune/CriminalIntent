package com.example.simune_lee.criminalintent;

//!< 为了兼容使用早起版本的support 中的Fragment引用
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Simune_Lee on 2016/3/10.
 */
public class CrimeFragment extends Fragment {

    private Crime mCrime;

    private EditText mTitleField;

    private Button mBtn;

    private Button mTimeBtn;

    private CheckBox mIsSovled;

    public static final String EXTRA_CRIME_ID =
            "com.example.simune_lee.crimeintent_id";

    public static final String DIALOG_DATE = "date";

    public static final int REQUEST_DATE = 0;

    public static final String DIALOG_TIME = "time";

    public static final int REQUEST_TIME = 1;

    public void updateDate()
    {
        mBtn.setText(mCrime.getDate().toString());
    }

    public void updateTime() { mTimeBtn.setText(mCrime.getDate().toString());}

    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
//        UUID crimeId = (UUID)getActivity().getIntent()
//                .getSerializableExtra(EXTRA_CRIME_ID);
        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);

        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);

        setHasOptionsMenu(true);
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,Bundle saveInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_crime,parent,false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            if (NavUtils.getParentActivityName(getActivity()) != null)
            {
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            }

        }

        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBtn = (Button)v.findViewById(R.id.crime_date);
        updateDate();
//        mBtn.setEnabled(false);
        mBtn.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v)
            {
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();

                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mCrime.getDate());

                dialog.setTargetFragment(CrimeFragment.this,REQUEST_DATE);

                dialog.show(fm,DIALOG_DATE);
            }
        });

        mTimeBtn = (Button)v.findViewById(R.id.crime_time);
        updateTime();
        mTimeBtn.setOnClickListener( new View.OnClickListener()
        {
            public void onClick(View v)
            {
               FragmentManager fm = getActivity()
                       .getSupportFragmentManager();

                TimePickerFragment timepickerfragment = TimePickerFragment.newInstance(mCrime.getDate());

                timepickerfragment.setTargetFragment(CrimeFragment.this,REQUEST_TIME);

                timepickerfragment.show(fm,DIALOG_TIME);
            }
        });
        mIsSovled = (CheckBox)v.findViewById(R.id.crime_solved);
        mIsSovled.setChecked(mCrime.isSolved());
        mIsSovled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });


        return v;
    }

    public static CrimeFragment newInstance(UUID crimeId)
    {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode ,int resultCode,Intent data)
    {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE)
        {
            Date date = (Date)data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        }
        else if (requestCode == REQUEST_TIME)
        {
            Date date = (Date)data
                    .getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mCrime.setDate(date);
            updateTime();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case android.R.id.home:

                if (NavUtils.getParentActivityName(getActivity()) != null)
            {
                NavUtils.navigateUpFromSameTask(getActivity());
            }

                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



