package a1pour1.hebergratuit.net.a1pour1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by SPORE on 24/02/2018.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private int yearChosen;
    private int monthChosen;
    private int dayChosen;

    public DatePickerFragment() {
        final Calendar c = Calendar.getInstance();
        this.yearChosen = c.get(Calendar.YEAR);
        this.monthChosen = c.get(Calendar.MONTH);
        this.dayChosen = c.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        this.yearChosen = year;
        this.monthChosen = month + 1;
        this.dayChosen = day;
    }

    public int getYearChosen() {
        return yearChosen;
    }

    public int getMonthChosen() {
        return monthChosen;
    }

    public int getDayChosen() {
        return dayChosen;
    }

    public void setYearChosen(int yearChosen) {
        this.yearChosen = yearChosen;
    }

    public void setMonthChosen(int monthChosen) {
        this.monthChosen = monthChosen;
    }

    public void setDayChosen(int dayChosen) {
        this.dayChosen = dayChosen;
    }
}