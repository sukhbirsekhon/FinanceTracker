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
    private List<RevenueTransactionModel> revenueList;

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

        monthlyAdapter = new MonthlyAdapter(getActivity().getApplicationContext());
        monthlyRecyclerView.setAdapter(monthlyAdapter);
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