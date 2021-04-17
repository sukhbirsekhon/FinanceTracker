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

import home.project.group.financetracker.Adapter.StatisticsAdapter;
import home.project.group.financetracker.R;

public class StatisticsFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    private StatisticsAdapter adapter;
    Button expenseViewBtn, revenueViewBtn;
    LinearLayout expenseStatisticsView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_statistics, container, false);

        expenseViewBtn = root.findViewById(R.id.expenseViewBtn);
        revenueViewBtn = root.findViewById(R.id.revenueViewBtn);

        expenseViewBtn.setOnClickListener(this);
        revenueViewBtn.setOnClickListener(this);

        expenseStatisticsView = root.findViewById(R.id.expenseStatisticsView);

        recyclerView = root.findViewById(R.id.chartsRecyclerview);

        getData();
        return root;
    }

    private void getData() {
        adapter = new StatisticsAdapter(getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.expenseViewBtn:
                expenseStatisticsView.setVisibility(View.VISIBLE);
                break;
            case R.id.revenueViewBtn:
                expenseStatisticsView.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}