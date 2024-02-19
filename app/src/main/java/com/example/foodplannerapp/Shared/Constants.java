package com.example.foodplannerapp.Shared;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.text.format.DateFormat;
import android.widget.Toast;

import androidx.navigation.NavController;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.auth_feature.view.LoginActivity;
import com.example.foodplannerapp.meals_feature.view.DatePickerDialogListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

public class Constants {
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    public static final String FAV_TABLE = "favourites";
    public static final String PLAN_TABLE = "plan";
    public static final String DAILY_MEAL_TABLE = "dailyMeal";

    public static String getDate()
    {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        int day = currentDate.getDayOfMonth();

        return year + "-" + month + "-" + day;
    }

    public static boolean isLogedIn(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("auth", context.MODE_PRIVATE);
        return preferences.getString("email",null)==null;
    }
    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
    public static void navigate(int id, NavController navController, SharedPreferences sharedPreferences,Activity activity)
    {
        if(id == R.id.homeFragment)
        {
            navController.navigate(R.id.homeFragment);
        }else {
            if(id == R.id.profileFragment2)
            {
                if(sharedPreferences.getString("email",null)==null)
                {
                    Constants.showDialog(activity, LoginActivity.class);
                }else
                    navController.navigate(R.id.profileFragment2);
            }else
            if(id == R.id.searchFragment2)
            {
                navController.navigate(R.id.searchFragment2);
            }else {
                if(id == R.id.favouritesFragment)
                {
                    if(sharedPreferences.getString("email",null)==null)
                    {
                        Constants.showDialog(activity, LoginActivity.class);
                    }else
                        navController.navigate(R.id.favouritesFragment);
                }else {
                    if(sharedPreferences.getString("email",null)==null)
                    {
                        Constants.showDialog(activity, LoginActivity.class);
                    }else
                        navController.navigate(R.id.weeklyPlanFragment);
                }
            }
        }
    }
    public static void showDialog(Activity context, Class activity)
    {
        new MaterialAlertDialogBuilder(context)
                .setTitle(R.string.dialogTitle)
                .setMessage(R.string.dialogMessge)
                .setPositiveButton("Sign Up", (dialog, which) -> {
                    Intent intent = new Intent(context,activity);
                   context.startActivity(intent);
                   context.finish();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Negative button action
                })
                .show();
    }

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
