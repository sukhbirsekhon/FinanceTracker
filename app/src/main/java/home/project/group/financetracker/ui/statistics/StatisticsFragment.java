package home.project.group.financetracker.ui.statistics;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import home.project.group.financetracker.R;

public class StatisticsFragment extends Fragment {

    private StatisticsViewModel statisticsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statisticsViewModel =
                ViewModelProviders.of(this).get(StatisticsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_statistics, container, false);

        PieChart pieChart = (PieChart) root.findViewById(R.id.pieChart);

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(18.5f, "Clothing"));
        entries.add(new PieEntry(26.7f, "Gas"));
        entries.add(new PieEntry(24.0f, "Sports"));
        entries.add(new PieEntry(30.8f, "Food"));

        PieDataSet set = new PieDataSet(entries, "");

        set.setColors(new int[]{R.color.green, R.color.yellow, R.color.red, R.color.blue}, this.getContext());
        set.setValueTextSize(0.1f);

        PieData data = new PieData(set);

        Description description = new Description();
        description.setEnabled(false);

        CharSequence title = "EXPENSES";

        pieChart.setData(data);
        pieChart.invalidate(); // refresh
        pieChart.setDescription(description);
        pieChart.setCenterText(title);

        return root;
    }
}