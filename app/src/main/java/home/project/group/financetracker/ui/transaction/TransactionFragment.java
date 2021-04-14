package home.project.group.financetracker.ui.transaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import home.project.group.financetracker.EntityClass.ExpenseTransactionModel;
import home.project.group.financetracker.EntityClass.RevenueTransactionModel;
import home.project.group.financetracker.R;

public class TransactionFragment extends Fragment implements View.OnClickListener {

    EditText name, amount, category, date, note;
    RadioButton radioBtnRevenue, radioBtnExpense;
    Button save;
    private TransactionViewModel transactionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        transactionViewModel =
                ViewModelProviders.of(this).get(TransactionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_transaction, container, false);

        radioBtnRevenue = (RadioButton) root.findViewById(R.id.radioBtnRevenue);
        radioBtnExpense = (RadioButton) root.findViewById(R.id.radioBtnExpense);
        name = root.findViewById(R.id.txtName);
        category = root.findViewById(R.id.txtCategory);
        date = root.findViewById(R.id.txtDate);
        note = root.findViewById(R.id.txtNote);
        amount = root.findViewById(R.id.txtAmount);
        save = (Button) root.findViewById(R.id.btnSave);

        save.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View view) {
        saveData();
    }

    private void saveData() {
        String name_txt = name.getText().toString().trim();
        double amount_txt = Double.parseDouble(amount.getText().toString().trim());
        String category_txt = category.getText().toString().trim();
        String date_txt = date.getText().toString().trim();
        String note_txt = note.getText().toString().trim();

        if(radioBtnRevenue.isChecked()){
            RevenueTransactionModel model = new RevenueTransactionModel();
            model.setRevenueName(name_txt);
            model.setAmount(amount_txt);
            model.setCategory(category_txt);
            model.setDate(date_txt);
            model.setNote(note_txt);
            DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().insertAllRevenueData(model);
        }

        if(radioBtnExpense.isChecked()){
            ExpenseTransactionModel model = new ExpenseTransactionModel();
            model.setExpenseName(name_txt);
            model.setAmount(amount_txt);
            model.setCategory(category_txt);
            model.setDate(date_txt);
            model.setNote(note_txt);
            DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().insertAllExpenseData(model);
        }

        Toast.makeText(getActivity(), (radioBtnRevenue.isChecked() ? "Revenue" : "Expense") + " data successfully saved", Toast.LENGTH_SHORT).show();
    }
}