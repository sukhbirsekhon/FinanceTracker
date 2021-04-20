package home.project.group.financetracker.ui.transaction;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import home.project.group.financetracker.EntityClass.CategoriesModel;
import home.project.group.financetracker.EntityClass.ExpenseTransactionModel;
import home.project.group.financetracker.EntityClass.RevenueTransactionModel;
import home.project.group.financetracker.R;

public class TransactionFragment extends Fragment implements View.OnClickListener {

    EditText name, amount, note;
    Spinner category;
    Date sqlDate;
    TextView date;
    RadioButton radioBtnRevenue, radioBtnExpense;
    Button save;
    private TransactionViewModel transactionViewModel;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private ArrayAdapter<String> dataAdapter;
    private List<String> categories = new ArrayList<>();
    private final String EXPENSE = "expense";
    private final String REVENUE = "revenue";
    private String category_txt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        transactionViewModel =
                ViewModelProviders.of(this).get(TransactionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_transaction, container, false);

        radioBtnRevenue = (RadioButton) root.findViewById(R.id.radioBtnRevenue);
        radioBtnExpense = (RadioButton) root.findViewById(R.id.radioBtnExpense);
        name = root.findViewById(R.id.txtName);
        category = (Spinner) root.findViewById(R.id.txtCategory);
        note = root.findViewById(R.id.txtNote);
        amount = root.findViewById(R.id.txtAmount);
        save = (Button) root.findViewById(R.id.btnSave);
        date = root.findViewById(R.id.txtDate);

        //Dummy data for categories table
        CategoriesModel categoriesModel1 = new CategoriesModel();
        categoriesModel1.setName("work");
        categoriesModel1.setType(REVENUE);
        CategoriesModel categoriesModel2 = new CategoriesModel();
        categoriesModel2.setName("grocery");
        categoriesModel2.setType("expense");
        CategoriesModel categoriesModel3 = new CategoriesModel();
        categoriesModel3.setName("gas");
        categoriesModel3.setType(EXPENSE);
        CategoriesModel checkDuplicate1 = DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().checkForDuplicateCategory(categoriesModel1.getName().toLowerCase());
        CategoriesModel checkDuplicate2 = DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().checkForDuplicateCategory(categoriesModel2.getName().toLowerCase());
        CategoriesModel checkDuplicate3 = DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().checkForDuplicateCategory(categoriesModel3.getName().toLowerCase());
        if(checkDuplicate1 == null) {
            DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().insertAllCategoriesData(categoriesModel1);
        }
        if(checkDuplicate2 == null) {
            DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().insertAllCategoriesData(categoriesModel2);
        }
        if(checkDuplicate3 == null) {
            DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().insertAllCategoriesData(categoriesModel3);
        }

        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        category.setAdapter(dataAdapter);

        save.setOnClickListener(this);
        date.setOnClickListener(this);
        radioBtnExpense.setOnClickListener(this);
        radioBtnRevenue.setOnClickListener(this);


        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String strDate = month + "-" + day + "-" + year;
                date.setText(strDate);
                sqlDate = new Date(year, month, day);
            }
        };
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = dataAdapter.getItem(position);
                if (item != null) {
                    category_txt = item.toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
            case R.id.radioBtnExpense:
                fillAdapter(EXPENSE);
                break;
            case R.id.radioBtnRevenue:
                fillAdapter(REVENUE);
                break;
        }
    }

    private void fillAdapter(String type) {
        List<CategoriesModel> typeCategories = DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().getAllTypeCategories(type);
        categories = new ArrayList<>();
        for(CategoriesModel typeCategory : typeCategories) {
            categories.add(typeCategory.getName());
        }
        dataAdapter.clear();
        if (categories != null){
            for (String object : categories) {
                dataAdapter.insert(object, dataAdapter.getCount());
            }
        }
        dataAdapter.notifyDataSetChanged();
    }

    private void saveData() {
        String name_txt = name.getText().toString().trim();
        double amount_txt = Double.parseDouble(amount.getText().toString().trim());
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