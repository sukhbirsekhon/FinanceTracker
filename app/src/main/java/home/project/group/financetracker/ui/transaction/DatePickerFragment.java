package home.project.group.financetracker.ui.transaction;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import java.util.Date;
import java.util.Calendar;

import home.project.group.financetracker.R;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    Date date;

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
        this.date = new Date(year, month, day);
        System.out.println("I am in the date picker fragment - "+this.date);
    }

    public Date getDate(){
        return this.date;
    }
}
