package home.project.group.financetracker.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import home.project.group.financetracker.Adapter.ExpenseAdapter;
import home.project.group.financetracker.EntityClass.ExpenseTransactionModel;
import home.project.group.financetracker.R;

public class CalendarFragment extends Fragment {

    private CalendarViewModel calendarViewModel;
    RecyclerView recyclerView;
    private List<ExpenseTransactionModel> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel =
                ViewModelProviders.of(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);

        recyclerView = root.findViewById(R.id.recyclerview);
        getData();
        return root;
    }

    private void getData() {
        list = new ArrayList<>();
        list = DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().getAllExpenseData();
        recyclerView.setAdapter(new ExpenseAdapter(getActivity().getApplicationContext(), list, new ExpenseAdapter.DeleteItemClickListener() {
            @Override
            public void onItemDelete(int position, int id) {
                DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().deleteExpenseData(id);
                getData();
            }
        }));
    }
}