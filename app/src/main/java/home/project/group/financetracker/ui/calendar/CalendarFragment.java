package home.project.group.financetracker.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import home.project.group.financetracker.Adapter.ExpenseAdapter;
import home.project.group.financetracker.Adapter.RevenueAdapter;
import home.project.group.financetracker.EntityClass.ExpenseTransactionModel;
import home.project.group.financetracker.EntityClass.RevenueTransactionModel;
import home.project.group.financetracker.R;
import home.project.group.financetracker.Utility.Theme;

public class CalendarFragment extends Fragment {

    RecyclerView recyclerView;
    private List<ExpenseTransactionModel> expenseList;
    private List<RevenueTransactionModel> revenueList;
    private ExpenseAdapter expenseAdapter;
    private RevenueAdapter revenueAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Fragment fragment = this;
        View root = Theme.themeDecider(inflater, fragment).inflate(R.layout.fragment_calendar, container, false);

        recyclerView = root.findViewById(R.id.recyclerview);
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
        recyclerView.setAdapter(concatAdapter);
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