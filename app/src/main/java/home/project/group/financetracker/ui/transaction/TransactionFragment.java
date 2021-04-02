package home.project.group.financetracker.ui.transaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import home.project.group.financetracker.EntityClass.ExpenseTransactionModel;
import home.project.group.financetracker.R;

public class TransactionFragment extends Fragment implements View.OnClickListener {

    private TransactionViewModel transactionViewModel;
    EditText expenseName, amount, category;
    Button save;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        transactionViewModel =
                ViewModelProviders.of(this).get(TransactionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_transaction, container, false);

        expenseName = root.findViewById(R.id.txtExpenseName);
        category = root.findViewById(R.id.txtCategory);
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
        String expenseName_txt = expenseName.getText().toString().trim();
        String amount_txt = amount.getText().toString().trim();
        String category_txt = category.getText().toString().trim();

        ExpenseTransactionModel model = new ExpenseTransactionModel();

        model.setExpenseName(expenseName_txt);
        model.setAmount(amount_txt);
        model.setCategory(category_txt);

        DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().insertAllExpenseData(model);

        Toast.makeText(getActivity(), "Data successfully saved", Toast.LENGTH_SHORT).show();
    }
}