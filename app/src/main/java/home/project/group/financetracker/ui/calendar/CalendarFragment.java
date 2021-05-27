package home.project.group.financetracker.ui.calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import home.project.group.financetracker.Adapter.MonthlyAdapter;
import home.project.group.financetracker.Adapter.TransactionAdapter;
import home.project.group.financetracker.EntityClass.CategoriesModel;
import home.project.group.financetracker.EntityClass.TransactionModel;
import home.project.group.financetracker.R;
import home.project.group.financetracker.Utility.Theme;

public class CalendarFragment extends Fragment implements View.OnClickListener {

    EditText txtName, txtAmount, txtNote;
    ConstraintLayout updateFormLayout;
    Spinner spinnerCategory;
    Date sqlDate;
    TextView txtDate;
    RadioButton radioBtnRevenue, radioBtnExpense;
    View updateForm;
    private int txtDateYear = Calendar.YEAR;
    private int txtDateMonth = Calendar.MONTH;
    private int txtDateDay = Calendar.DAY_OF_MONTH;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private List<String> categories = new ArrayList<>();
    private ArrayAdapter<String> dataAdapter;
    private String category_txt;

    RecyclerView dailyRecyclerView, monthlyRecyclerView;

    private List<TransactionModel> transactionList;
    private List<TransactionModel> monthlyTransaction;

    private List<Double> jan;
    private List<Double> feb;
    private List<Double> mar;
    private List<Double> apr;
    private List<Double> may;
    private List<Double> jun;
    private List<Double> jul;
    private List<Double> aug;
    private List<Double> sep;
    private List<Double> oct;
    private List<Double> nov;
    private List<Double> dec;

    private List<List<Double>> monthly;

    private MonthlyAdapter monthlyAdapter;

    private TransactionAdapter transactionAdapter;

    Button dailyViewBtn, monthlyViewBtn;
    LinearLayout dailyView, monthlyView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Fragment fragment = this;
        View root = Theme.themeDecider(inflater, fragment).inflate(R.layout.fragment_calendar, container, false);
        updateForm = Theme.themeDecider(inflater, fragment).inflate(R.layout.calendar_update_form, container, false);
        setHasOptionsMenu(true);

        radioBtnRevenue = (RadioButton) updateForm.findViewById(R.id.radioBtnUpdateRevenue);
        radioBtnExpense = (RadioButton) updateForm.findViewById(R.id.radioBtnUpdateExpense);
        txtName = updateForm.findViewById(R.id.txtUpdateName);
        updateFormLayout = (ConstraintLayout) updateForm.findViewById(R.id.calendarUpdateFormLayout);
        spinnerCategory = (Spinner) updateForm.findViewById(R.id.txtUpdateCategory);
        txtNote = updateForm.findViewById(R.id.txtUpdateNote);
        txtAmount = updateForm.findViewById(R.id.txtUpdateAmount);
        txtDate = updateForm.findViewById(R.id.txtUpdateDate);

        dataAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(dataAdapter);

        radioBtnExpense.setOnClickListener(this);
        radioBtnRevenue.setOnClickListener(this);
        txtDate.setOnClickListener(this);

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String strDate = month + "-" + day + "-" + year;
                txtDate.setText(strDate);
                sqlDate = new Date(year, month, day);
            }
        };
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = dataAdapter.getItem(position);
                if (item != null) {
                    category_txt = item.toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        dailyRecyclerView = root.findViewById(R.id.dailyRecyclerview);
        monthlyRecyclerView = root.findViewById(R.id.monthlyRecyclerView);

        dailyViewBtn = root.findViewById(R.id.dailyViewBtn);
        monthlyViewBtn = root.findViewById(R.id.monthlyViewBtn);

        dailyViewBtn.setOnClickListener(this);
        monthlyViewBtn.setOnClickListener(this);

        dailyView = root.findViewById(R.id.dailyView);
        monthlyView = root.findViewById(R.id.monthlyView);

        getData();
        return root;
    }

    /**
     * Get data for the recycler view
     */
    private void getData() {
        /**
         * Initialize the list and store all expense/revenue data from room
         */

        transactionList = new ArrayList<>();
        transactionList = DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().getAllTransactionData();

        monthlyTransaction = new ArrayList<>();
        monthlyTransaction = DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().monthlyTransaction();

        jan = new ArrayList<>();
        feb = new ArrayList<>();
        mar = new ArrayList<>();
        apr = new ArrayList<>();
        may = new ArrayList<>();
        jun = new ArrayList<>();
        jul = new ArrayList<>();
        aug = new ArrayList<>();
        sep = new ArrayList<>();
        oct = new ArrayList<>();
        nov = new ArrayList<>();
        dec = new ArrayList<>();

        monthly = new ArrayList<>();

        for (int i = 0; i < monthlyTransaction.size(); i++) {
            if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 1) {
                jan.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 2) {
                feb.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 3) {
                mar.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 4) {
                apr.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 5) {
                may.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 6) {
                jun.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 7) {
                jul.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 8) {
                aug.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 9) {
                sep.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 10) {
                oct.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 11) {
                nov.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 0) {
                dec.add(monthlyTransaction.get(i).getAmount());
            }
        }

        addExpenses(jan, monthly);
        addExpenses(feb, monthly);
        addExpenses(mar, monthly);
        addExpenses(apr, monthly);
        addExpenses(may, monthly);
        addExpenses(jun, monthly);
        addExpenses(jul, monthly);
        addExpenses(aug, monthly);
        addExpenses(sep, monthly);
        addExpenses(oct, monthly);
        addExpenses(nov, monthly);
        addExpenses(dec, monthly);

        for (int i = 0; i < monthlyTransaction.size(); i++) {
            if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 1) {
                jan.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 2) {
                feb.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 3) {
                mar.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 4) {
                apr.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 5) {
                may.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 6) {
                jun.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 7) {
                jul.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 8) {
                aug.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 9) {
                sep.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 10) {
                oct.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 11) {
                nov.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 0) {
                dec.add(monthlyTransaction.get(i).getAmount());
            }
        }

        addRevenues(jan, monthly);
        addRevenues(feb, monthly);
        addRevenues(mar, monthly);
        addRevenues(apr, monthly);
        addRevenues(may, monthly);
        addRevenues(jun, monthly);
        addRevenues(jul, monthly);
        addRevenues(aug, monthly);
        addRevenues(sep, monthly);
        addRevenues(oct, monthly);
        addRevenues(nov, monthly);
        addRevenues(dec, monthly);

        for (int i = 0; i < monthly.size(); i++) {
            Log.d("Expense/Revenue", monthly.get(i).toString());
        }

        transactionAdapter = new TransactionAdapter(getActivity().getApplicationContext(), transactionList, new TransactionAdapter.DeleteItemClickListener() {
            /**
             * Delete individual card if user click delete button
             *
             * @param position
             * @param id
             */
            @Override
            public void onItemDelete(int position, int id) {
                DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().deleteTransactionData(id);
                getData();
            }
            }, new TransactionAdapter.UpdateItemClickListener() {
                @Override
                public void onItemUpdate(int key, String type, String name, String amount, String note, String date, java.util.Date dateObject, String category) {
                    cardUpdateView(key, type, name, amount, note, date, dateObject, category);
                }
        });

        dailyRecyclerView.setAdapter(transactionAdapter);

        monthlyAdapter = new MonthlyAdapter(getActivity().getApplicationContext(), monthly);
        monthlyRecyclerView.setAdapter(monthlyAdapter);
    }

    private void cardUpdateView(int key, String type, String name, String amount, String note, String date, java.util.Date dateObject, String category) {
        final int id = key;
        final String previousType = type;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Update a transaction");
        if(type.equals("R")) {
            radioBtnRevenue.toggle();
        } else {
            radioBtnExpense.toggle();
        }
        txtName.setText(name);
        txtAmount.setText(amount);
        txtNote.setText(note);
        txtDate.setText(date);
        fillCategoryAdapter(type);
        txtDateYear = dateObject.getYear();
        txtDateMonth = dateObject.getMonth();
        txtDateDay = dateObject.getDay();
        spinnerCategory.setSelection(dataAdapter.getPosition(category));
        if(updateFormLayout.getParent() != null) {
            ((ViewGroup)updateFormLayout.getParent()).removeView(updateFormLayout);
        }
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(updateFormLayout);
        builder.setView(linearLayout);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String updatedName = txtName.getText().toString().trim();
                double updatedAmount = Double.parseDouble(txtAmount.getText().toString().trim());
                String updatedNote = txtNote.getText().toString().trim();
                if(radioBtnExpense.isChecked()) {
                    if(previousType.equals("R")) {
                        TransactionModel model = new TransactionModel();
                        model.setType("E");
                        model.setName(updatedName);
                        model.setAmount(updatedAmount);
                        model.setNote(updatedNote);
                        model.setCategory(category_txt);
                        model.setDate(sqlDate);
                        DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().deleteTransactionData(id);
                        DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().insertAllTransactionData(model);
                        Toast.makeText(getActivity(), "Successfully converted to Expense data", Toast.LENGTH_SHORT).show();
                    } else {
                        DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().updateTransactionData(id, "E", updatedName, updatedAmount, category_txt, sqlDate, updatedNote);
                        Toast.makeText(getActivity(), "Successfully updated the Expense data", Toast.LENGTH_SHORT).show();
                    }
                }

                if(radioBtnRevenue.isChecked()) {
                    if(previousType.equals("E")) {
                        TransactionModel model = new TransactionModel();
                        model.setType("R");
                        model.setName(updatedName);
                        model.setAmount(updatedAmount);
                        model.setNote(updatedNote);
                        model.setCategory(category_txt);
                        model.setDate(sqlDate);
                        DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().deleteTransactionData(id);
                        DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().insertAllTransactionData(model);
                        Toast.makeText(getActivity(), "Successfully converted to Revenue data", Toast.LENGTH_SHORT).show();
                    } else {
                        DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().updateTransactionData(id, "R", updatedName, updatedAmount, category_txt, sqlDate, updatedNote);
                        Toast.makeText(getActivity(), "Successfully updated the Revenue data", Toast.LENGTH_SHORT).show();
                    }
                }
                getData();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dailyRecyclerView.setAdapter(transactionAdapter);

        monthlyAdapter = new MonthlyAdapter(getActivity().getApplicationContext(), monthly);
        monthlyRecyclerView.setAdapter(monthlyAdapter);
        builder.show();
    }

    private void fillCategoryAdapter(String type) {
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

    /**
     * Get a total of all expenses in a specific month
     *
     * @param month
     */
    private void addExpenses(List<Double> month, List<List<Double>> monthly) {
        double sum = 0;
        for (int i = 0; i < month.size(); i++) {
            sum += month.get(i);
        }
        month.clear();
        month.add(sum);
        monthly.add(month);
    }

    /**
     * Get a total of all specific month's data and subtract expenses to get revenue
     *
     * @param month
     */
    private void addRevenues(List<Double> month, List<List<Double>> monthly) {
        double expenses = month.get(0);
        double sum = 0;
        for (int i = 0; i < month.size(); i++) {
            sum += month.get(i);
        }
        month.clear();
        double revenue = sum - expenses;
        month.add(expenses);
        month.add(revenue);
        monthly.remove(month);
        monthly.add(month);
    }


    /**
     * Set search menu to calendar fragment or page
     *
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        /**
         * Filter out the recycler view list in real-time
         */
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                transactionAdapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dailyViewBtn:
                monthlyView.setVisibility(View.GONE);
                dailyView.setVisibility(View.VISIBLE);
                break;
            case R.id.monthlyViewBtn:
                dailyView.setVisibility(View.GONE);
                monthlyView.setVisibility(View.VISIBLE);
                break;
            case R.id.txtUpdateDate:
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
                datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePicker.updateDate(txtDateYear, txtDateMonth, txtDateDay);
                datePicker.show();
                break;
            case R.id.radioBtnUpdateExpense:
                fillCategoryAdapter("E");
                break;
            case R.id.radioBtnUpdateRevenue:
                fillCategoryAdapter("R");
                break;
            default:
                break;
        }
    }

}