package home.project.group.financetracker.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import home.project.group.financetracker.Adapter.ExpenseAdapter;
import home.project.group.financetracker.Adapter.MonthlyAdapter;
import home.project.group.financetracker.Adapter.RevenueAdapter;
import home.project.group.financetracker.EntityClass.ExpenseTransactionModel;
import home.project.group.financetracker.EntityClass.RevenueTransactionModel;
import home.project.group.financetracker.R;
import home.project.group.financetracker.Utility.Theme;

public class CalendarFragment extends Fragment implements View.OnClickListener {

    RecyclerView dailyRecyclerView, monthlyRecyclerView;

    private List<ExpenseTransactionModel> expenseList;
    private List<ExpenseTransactionModel> monthlyExpense;
    private List<RevenueTransactionModel> revenueList;

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

    private ExpenseAdapter expenseAdapter;
    private RevenueAdapter revenueAdapter;
    private MonthlyAdapter monthlyAdapter;

    Button dailyViewBtn, monthlyViewBtn;
    LinearLayout dailyView, monthlyView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Fragment fragment = this;
        View root = Theme.themeDecider(inflater, fragment).inflate(R.layout.fragment_calendar, container, false);
        setHasOptionsMenu(true);

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
        expenseList = new ArrayList<>();
        expenseList = DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().getAllExpenseData();

        revenueList = new ArrayList<>();
        revenueList = DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().getAllRevenueData();

        monthlyExpense = new ArrayList<>();
        monthlyExpense = DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().monthlyExpense();

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

        for (int i = 0; i < monthlyExpense.size(); i++) {
            if (monthlyExpense.get(i).getDate().getMonth() == 1) {
                addMonthlyExpenses(monthlyExpense, jan, monthly, i);
            } else if (monthlyExpense.get(i).getDate().getMonth() == 2) {
                addMonthlyExpenses(monthlyExpense, feb, monthly, i);
            } else if (monthlyExpense.get(i).getDate().getMonth() == 3) {
                addMonthlyExpenses(monthlyExpense, mar, monthly, i);
            } else if (monthlyExpense.get(i).getDate().getMonth() == 4) {
                addMonthlyExpenses(monthlyExpense, apr, monthly, i);
            } else if (monthlyExpense.get(i).getDate().getMonth() == 5) {
                addMonthlyExpenses(monthlyExpense, may, monthly, i);
            } else if (monthlyExpense.get(i).getDate().getMonth() == 6) {
                addMonthlyExpenses(monthlyExpense, jun, monthly, i);
            } else if (monthlyExpense.get(i).getDate().getMonth() == 7) {
                addMonthlyExpenses(monthlyExpense, jul, monthly, i);
            } else if (monthlyExpense.get(i).getDate().getMonth() == 8) {
                addMonthlyExpenses(monthlyExpense, aug, monthly, i);
            } else if (monthlyExpense.get(i).getDate().getMonth() == 9) {
                addMonthlyExpenses(monthlyExpense, sep, monthly, i);
            } else if (monthlyExpense.get(i).getDate().getMonth() == 10) {
                addMonthlyExpenses(monthlyExpense, oct, monthly, i);
            } else if (monthlyExpense.get(i).getDate().getMonth() == 11) {
                addMonthlyExpenses(monthlyExpense, nov, monthly, i);
            } else if (monthlyExpense.get(i).getDate().getMonth() == 0) {
                addMonthlyExpenses(monthlyExpense, dec, monthly, i);
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

        System.out.println(jan);
        System.out.println(feb);
        System.out.println(apr);
        System.out.println(monthly);
        for (int i = 0; i < monthly.size(); i++) {
            System.out.println(monthly.get(i).get(0));
        }


        /**
         * Use adapter to fill recycler view with data
         */
        expenseAdapter = new ExpenseAdapter(getActivity().getApplicationContext(), expenseList, new ExpenseAdapter.DeleteItemClickListener() {
            /**
             * Delete individual card if user click delete button
             *
             * @param position
             * @param id
             */
            @Override
            public void onItemDelete(int position, int id) {
                DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().deleteExpenseData(id);
                getData();
            }
        });
        revenueAdapter = new RevenueAdapter(getActivity().getApplicationContext(), revenueList, new RevenueAdapter.DeleteItemClickListener() {
            /**
             * Delete individual card if user click delete button
             * @param position
             * @param id
             */
            @Override
            public void onItemDelete(int position, int id) {
                DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().deleteRevenueData(id);
                getData();
            }
        });

        ConcatAdapter concatAdapter = new ConcatAdapter(expenseAdapter, revenueAdapter);
        dailyRecyclerView.setAdapter(concatAdapter);

        monthlyAdapter = new MonthlyAdapter(getActivity().getApplicationContext(), monthlyExpense, monthly);
        monthlyRecyclerView.setAdapter(monthlyAdapter);
    }

    /**
     * Add month's expenses to appropriate month arraylist
     *
     * @param monthlyExpense
     * @param monthName
     * @param monthly
     * @param i
     */
    private void addMonthlyExpenses(List<ExpenseTransactionModel> monthlyExpense, List<Double> monthName, List<List<Double>> monthly, int i) {
        monthName.add(monthlyExpense.get(i).getAmount());
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
            default:
                break;
        }
    }

}