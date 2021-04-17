package home.project.group.financetracker.ui.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import home.project.group.financetracker.Adapter.ExpenseStatisticsAdapter;
import home.project.group.financetracker.Adapter.RevenueStatisticsAdapter;
import home.project.group.financetracker.R;

public class StatisticsFragment extends Fragment implements View.OnClickListener {

    RecyclerView expenseRecyclerView, revenueRecyclerView;
    private ExpenseStatisticsAdapter expenseAdapter;
    private RevenueStatisticsAdapter revenueAdapter;
    Button expenseViewBtn, revenueViewBtn;
    LinearLayout expenseStatisticsView, revenueStatisticsView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_statistics, container, false);

        expenseViewBtn = root.findViewById(R.id.expenseViewBtn);
        revenueViewBtn = root.findViewById(R.id.revenueViewBtn);

        expenseViewBtn.setOnClickListener(this);
        revenueViewBtn.setOnClickListener(this);

        expenseStatisticsView = root.findViewById(R.id.expenseStatisticsView);
        revenueStatisticsView = root.findViewById(R.id.revenueStatisticsView);

        expenseRecyclerView = root.findViewById(R.id.expenseRecyclerview);
        revenueRecyclerView = root.findViewById(R.id.revenueRecyclerview);

        getData();
        return root;
    }

    private void getData() {
        expenseAdapter = new ExpenseStatisticsAdapter(getActivity().getApplicationContext());
        expenseRecyclerView.setAdapter(expenseAdapter);

        revenueAdapter = new RevenueStatisticsAdapter(getActivity().getApplicationContext());
        revenueRecyclerView.setAdapter(revenueAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.expenseViewBtn:
                revenueStatisticsView.setVisibility(View.GONE);
                expenseStatisticsView.setVisibility(View.VISIBLE);
                break;
            case R.id.revenueViewBtn:
                expenseStatisticsView.setVisibility(View.GONE);
                revenueStatisticsView.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
}