package home.project.group.financetracker.ui.statistics;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import home.project.group.financetracker.EntityClass.TransactionModel;
import home.project.group.financetracker.R;
import home.project.group.financetracker.Utility.Theme;
import home.project.group.financetracker.ui.statistics.DatabaseClass;

public class StatisticsFragment extends Fragment implements View.OnClickListener {

    Button expenseViewBtn, revenueViewBtn;
    LinearLayout expenseStatisticsView, revenueStatisticsView;

    PieChart expensePieChart, revenuePieChart;
    BarChart expenseBarChart, revenueBarChart;

    List<TransactionModel> transactionList;
    List<String> expenseCategories;
    List<Double> expenseAmount;
    List<String> uniqueExpenseCategoriesList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Fragment fragment = this;
        View root = Theme.themeDecider(inflater, fragment).inflate(R.layout.fragment_statistics, container, false);

        expenseViewBtn = root.findViewById(R.id.expenseViewBtn);
        revenueViewBtn = root.findViewById(R.id.revenueViewBtn);

        expenseViewBtn.setOnClickListener(this);
        revenueViewBtn.setOnClickListener(this);

        expenseStatisticsView = root.findViewById(R.id.expenseStatisticsView);
        revenueStatisticsView = root.findViewById(R.id.revenueStatisticsView);

//        expenseBarChart = root.findViewById(R.id.expenseBarChart);
        expensePieChart = root.findViewById(R.id.expensePieChart);

        revenueBarChart = root.findViewById(R.id.revenueBarChart);
        revenuePieChart = root.findViewById(R.id.revenuePieChart);

        getData();
        return root;
    }

    private void getData() {

        expenseCharts();
        revenueCharts();
    }

    private void expenseCharts() {
        /**
         * Pie chart
         */
        transactionList = new ArrayList<>();
        transactionList = DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().getAllTransactionData();

        expenseCategories = new ArrayList<>();
        expenseAmount = new ArrayList<>();

        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).getType().equals("E")) {
                expenseCategories.add(transactionList.get(i).getCategory());
                expenseAmount.add(transactionList.get(i).getAmount());
            }
        }

        double totalExpense = 0;
        for (int i = 0; i < expenseAmount.size(); i++) {
            totalExpense += expenseAmount.get(i);
        }

        uniqueExpenseCategoriesList = removeDuplicates(expenseCategories);

        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < uniqueExpenseCategoriesList.size(); i++) {
            entries.add(new PieEntry((float) round(((expenseAmount.get(i) / totalExpense) * 100), 1), uniqueExpenseCategoriesList.get(i)));
            System.out.println(uniqueExpenseCategoriesList.get(i));
            System.out.println((float) round(((expenseAmount.get(i) / totalExpense) * 100), 1));
        }
        /*entries.add(new PieEntry(18.5f, "Clothing"));
        entries.add(new PieEntry(26.7f, "Gas"));
        entries.add(new PieEntry(24.0f, "Sports"));
        entries.add(new PieEntry(30.8f, "Food"));*/

        PieDataSet set = new PieDataSet(entries, "");

        set.setColors(new int[]{R.color.green, R.color.yellow, R.color.red, R.color.blue}, getContext());
        set.setValueTextSize(0.1f);

        PieData data = new PieData(set);

        Description description = new Description();
        description.setEnabled(false);

        CharSequence title = "EXPENSES";

        expensePieChart.setData(data);
        expensePieChart.setDescription(description);
        expensePieChart.setCenterText(title);
        expensePieChart.notifyDataSetChanged();
        expensePieChart.invalidate(); // refresh


    }

    private void revenueCharts() {
        /**
         * Pie chart
         */
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(18.5f, "Clothing"));
        entries.add(new PieEntry(26.7f, "Gas"));
        entries.add(new PieEntry(24.0f, "Sports"));
        entries.add(new PieEntry(30.8f, "Food"));

        PieDataSet set = new PieDataSet(entries, "");

        set.setColors(new int[]{R.color.green, R.color.yellow, R.color.red, R.color.blue}, getContext());
        set.setValueTextSize(0.1f);

        PieData data = new PieData(set);

        Description description = new Description();
        description.setEnabled(false);

        CharSequence title = "EXPENSES";

        revenuePieChart.setData(data);
        revenuePieChart.setDescription(description);
        revenuePieChart.setCenterText(title);
        revenuePieChart.notifyDataSetChanged();
        revenuePieChart.invalidate(); // refresh

        /**
         * Bar Chart
         */
        List<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0f, 30f));
        barEntries.add(new BarEntry(1f, 80f));
        barEntries.add(new BarEntry(2f, 60f));
        barEntries.add(new BarEntry(3f, 50f));
        // gap of 2f
        barEntries.add(new BarEntry(5f, 70f));
        barEntries.add(new BarEntry(6f, 60f));
        BarDataSet barDataSet = new BarDataSet(barEntries, "BarDataSet");

        XAxis xAxis = revenueBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(8f);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(-45);

        YAxis right = revenueBarChart.getAxisRight();
        right.setDrawLabels(false); // no axis labels
        right.setDrawAxisLine(false); // no axis line
        right.setDrawGridLines(false); // no grid lines
        right.setDrawZeroLine(true);

        YAxis left = revenueBarChart.getAxisLeft();
        left.setDrawLabels(false); // no axis labels
        left.setDrawAxisLine(false); // no axis line
        left.setDrawGridLines(false); // no grid lines
        left.setDrawZeroLine(true);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.9f); // set custom bar width
        revenueBarChart.setData(barData);
        revenueBarChart.setFitBars(true); // make the x-axis fit exactly all bars
        revenueBarChart.setDrawGridBackground(false);
        revenueBarChart.setDescription(description);
        revenueBarChart.animateXY(2000, 2000);
        revenueBarChart.setScaleEnabled(true);
        revenueBarChart.setDrawValueAboveBar(true);
        revenueBarChart.setDrawGridBackground(false);
        revenueBarChart.setDoubleTapToZoomEnabled(true);
        revenueBarChart.setPinchZoom(true);
        barDataSet.setColor(Color.parseColor("#606d5b"));
        revenueBarChart.invalidate(); // refresh
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

    /**
     * Round decimals to specific nth place
     *
     * @param value     84.124
     * @param precision 1
     * @return 84.1
     */
    private static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public static <T> List<T> removeDuplicates(List<T> list) {
        // Create a new ArrayList
        List<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }

}