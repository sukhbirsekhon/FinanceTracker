package home.project.group.financetracker.ui.transaction;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.sql.Date;
import java.util.Calendar;

import home.project.group.financetracker.EntityClass.ExpenseTransactionModel;
import home.project.group.financetracker.EntityClass.RevenueTransactionModel;
import home.project.group.financetracker.R;
import home.project.group.financetracker.Utility.Theme;

public class TransactionFragment extends Fragment implements View.OnClickListener {

    EditText name, amount, category, note;
    Date sqlDate;
    TextView date;
    RadioButton radioBtnRevenue, radioBtnExpense;
    Button save;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Fragment fragment = this;
        View root = Theme.themeDecider(inflater, fragment).inflate(R.layout.fragment_transaction, container, false);

        radioBtnRevenue = (RadioButton) root.findViewById(R.id.radioBtnRevenue);
        radioBtnExpense = (RadioButton) root.findViewById(R.id.radioBtnExpense);
        name = root.findViewById(R.id.txtName);
        category = root.findViewById(R.id.txtCategory);
        note = root.findViewById(R.id.txtNote);
        amount = root.findViewById(R.id.txtAmount);
        save = (Button) root.findViewById(R.id.btnSave);
        date = root.findViewById(R.id.txtDate);

        save.setOnClickListener(this);
        date.setOnClickListener(this);

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String strDate = month + "-" + day + "-" + year;
                date.setText(strDate);
                sqlDate = new Date(year, month, day);
            }
        };

        return root;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnSave:
                saveData();
                break;
            case R.id.txtDate:
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;
        }
    }

    private void saveData() {
        String name_txt = name.getText().toString().trim();
        double amount_txt = Double.parseDouble(amount.getText().toString().trim());
        String category_txt = category.getText().toString().trim();
        String note_txt = note.getText().toString().trim();

        if(radioBtnRevenue.isChecked()){
            RevenueTransactionModel model = new RevenueTransactionModel();
            model.setRevenueName(name_txt);
            model.setAmount(amount_txt);
            model.setCategory(category_txt);
            model.setDate(sqlDate);
            model.setNote(note_txt);
            DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().insertAllRevenueData(model);
        }

        if(radioBtnExpense.isChecked()){
            ExpenseTransactionModel model = new ExpenseTransactionModel();
            model.setExpenseName(name_txt);
            model.setAmount(amount_txt);
            model.setCategory(category_txt);
            model.setDate(sqlDate);
            model.setNote(note_txt);
            DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().insertAllExpenseData(model);
        }

        Toast.makeText(getActivity(), (radioBtnRevenue.isChecked() ? "Revenue" : "Expense") + " data successfully saved", Toast.LENGTH_SHORT).show();
    }
}