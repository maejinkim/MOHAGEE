package com.example.maedin.mohagee.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;
import android.support.v4.app.Fragment;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.activity.MainActivity;


public class TimePickerFragment extends DialogFragment {

    public TimePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker

        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), (SearchFragment)((MainActivity)getActivity()).getSupportFragmentManager().findFragmentByTag("search_fragment"), hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }



}
