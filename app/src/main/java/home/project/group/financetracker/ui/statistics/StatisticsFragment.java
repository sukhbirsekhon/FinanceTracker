package home.project.group.financetracker.ui.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import home.project.group.financetracker.Adapter.StatisticsAdapter;
import home.project.group.financetracker.R;

public class StatisticsFragment extends Fragment {

    RecyclerView recyclerView;
    private StatisticsAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_statistics, container, false);

        recyclerView = root.findViewById(R.id.chartsRecyclerview);
        getData();
        return root;
    }

    private void getData() {

        adapter = new StatisticsAdapter(getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}