package home.project.group.financetracker.ui.calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import home.project.group.financetracker.Adapter.ExpenseAdapter;
import home.project.group.financetracker.Adapter.RevenueAdapter;
import home.project.group.financetracker.EntityClass.CategoriesModel;
import home.project.group.financetracker.EntityClass.ExpenseTransactionModel;
import home.project.group.financetracker.EntityClass.RevenueTransactionModel;
import home.project.group.financetracker.R;
import home.project.group.financetracker.Utility.Theme;

public class CalendarFragment extends Fragment {

    EditText txtName, txtAmount, txtNote;
    ConstraintLayout updateFormLayout;
    Spinner spinnerCategory;
    Date sqlDate;
    TextView txtDate;
    RadioButton radioBtnRevenue, radioBtnExpense;
    RecyclerView recyclerView;
    View updateForm;
    private List<ExpenseTransactionModel> expenseList;
    private List<RevenueTransactionModel> revenueList;
    private ExpenseAdapter expenseAdapter;
    private RevenueAdapter revenueAdapter;
    private List<String> categories = new ArrayList<>();
    private ArrayAdapter<String> dataAdapter;

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
        recyclerView = root.findViewById(R.id.recyclerview);

        dataAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(dataAdapter);

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
        expenseList = new ArrayList<>();
        expenseList = DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().getAllExpenseData();

        revenueList = new ArrayList<>();
        revenueList = DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().getAllRevenueData();

        /**
         * Use adapter to fill recycler view with data
         */
        expenseAdapter = new ExpenseAdapter(getActivity().getApplicationContext(), expenseList, new ExpenseAdapter.DeleteItemClickListener() {
            /**
             * Delete individual card if user click delete button
             * @param position
             * @param id
             */
            @Override
            public void onItemDelete(int position, int id) {
                DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().deleteExpenseData(id);
                getData();
            }
        }, new ExpenseAdapter.UpdateClickListener() {
            @Override
            public void onBtnClick(String type, String name, String amount, String note, String date, String category) {
                showCategoryUpdateView(type, name, amount, note, date, category);
            }
        });
        revenueAdapter = new RevenueAdapter(getActivity().getApplicationContext(), revenueList, new RevenueAdapter.DeleteItemClickListener() {
            /**
             * Delete individual card if user click delete button
             *
             * @param position
             * @param id
             */
            @Override
            public void onItemDelete(int position, int id) {
                DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().deleteRevenueData(id);
                getData();
            }
        }, new RevenueAdapter.UpdateClickListener() {
            @Override
            public void onBtnClick(String type, String name, String amount, String note, String date, String category) {
                showCategoryUpdateView(type, name, amount, note, date, category);
            }
        });
        ConcatAdapter concatAdapter = new ConcatAdapter(expenseAdapter, revenueAdapter);
        recyclerView.setAdapter(concatAdapter);
    }

    private void showCategoryUpdateView(String type, String name, String amount, String note, String date, String category) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Update a transaction");
        if(type == "revenue"){
            radioBtnRevenue.toggle();
        } else {
            radioBtnExpense.toggle();
        }
        txtName.setText(name);
        txtAmount.setText(amount);
        txtNote.setText(note);
        txtDate.setText(date);
        fillCategoryAdapter(type);
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

                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void fillCategoryAdapter(String type) {
        List<CategoriesModel> typeCategories = new ArrayList<>();
        typeCategories = DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().getAllTypeCategories(type);
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
                expenseAdapter.getFilter().filter(newText);
                revenueAdapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}