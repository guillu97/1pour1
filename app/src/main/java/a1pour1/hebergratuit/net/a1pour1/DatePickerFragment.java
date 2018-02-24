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
        this.monthChosen = month;
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
}