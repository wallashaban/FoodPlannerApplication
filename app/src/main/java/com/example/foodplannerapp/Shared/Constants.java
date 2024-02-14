package com.example.foodplannerapp.Shared;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.example.foodplannerapp.MainActivity;
import com.example.foodplannerapp.meals_feature.view.DatePickerDialogListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Constants {
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    public static final String FAV_TABLE = "favourites";
    public static final String PLAN_TABLE = "plan";

    public static void showDatePicker(Context context,DatePickerDialogListener listener) {
        Calendar calendar = Calendar.getInstance();

        // Set minimum and maximum dates
        long minDate = calendar.getTimeInMillis();
        calendar.add(Calendar.DATE, 7); // Add 7 days to current date
        long maxDate = calendar.getTimeInMillis();

        // Create DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (view, year, month, dayOfMonth) -> {
                    // OnDateSetListener to get selected date
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, dayOfMonth);

                    // Pass the selected date back to the listener
                    listener.onDateSet(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        // Set min and max dates
        datePickerDialog.getDatePicker().setMinDate(minDate);
        datePickerDialog.getDatePicker().setMaxDate(maxDate);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }
    public static void showDateTimePicker(Context context) {
        Calendar calendar = Calendar.getInstance();

        // Set minimum date
        long minDate = calendar.getTimeInMillis();
        calendar.add(Calendar.DATE, 7); // Add 7 days to current date
        long maxDate = calendar.getTimeInMillis();

        // Create DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (view, year, month, dayOfMonth) -> {
                    // OnDateSetListener to get selected date
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, dayOfMonth);

                    // Create TimePickerDialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                            (timeView, hourOfDay, minute) -> {
                                // OnTimeSetListener to get selected time
                                selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                selectedDate.set(Calendar.MINUTE, minute);

                                // Use selectedDate for further processing
                                // For example, display the selected date and time
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                                String formattedDateTime = sdf.format(selectedDate.getTime());
                                Toast.makeText(context, "Selected Date & Time: " + formattedDateTime, Toast.LENGTH_SHORT).show();
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            DateFormat.is24HourFormat(context));

                    timePickerDialog.show();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        // Set min and max dates
        datePickerDialog.getDatePicker().setMinDate(minDate);
        datePickerDialog.getDatePicker().setMaxDate(maxDate);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }
}
