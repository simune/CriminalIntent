package com.example.simune_lee.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Simune_Lee on 2016/3/28.
 */
public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE =
            "com.example.simune_lee.criminalintent.date";

    private Date mDate;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        //!< 获取到传入的date值
        mDate = (Date)getArguments().getSerializable(EXTRA_DATE);

        //Create a Calender to get the number of year month and day;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_date, null);

        DatePicker datePicker = (DatePicker)v.findViewById(R.id.dialog_date_datepicker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //!< Translate year month and day into a date object using calendar

                mDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();

                //!< Update argument to preserve selected value on ratation
                getArguments().putSerializable(EXTRA_DATE, mDate);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
//                .setPositiveButton(android.R.string.ok,null)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(Activity.RESULT_OK);
                            }
                        })
                .create();
    }

    public static DatePickerFragment newInstance(Date date)
    {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private void sendResult(int resultcode)
    {
        if (getTargetFragment() == null)
        {
            return;
        }

        Intent i = new Intent();
        i.putExtra(EXTRA_DATE,mDate);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(),resultcode,i);

    }
}
