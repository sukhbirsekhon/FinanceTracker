package home.project.group.financetracker.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;

import home.project.group.financetracker.R;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.ViewHolder> {

    Context context;

    public StatisticsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.charts, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        /**
         * Pie chart
         */
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(18.5f, "Clothing"));
        entries.add(new PieEntry(26.7f, "Gas"));
        entries.add(new PieEntry(24.0f, "Sports"));
        entries.add(new PieEntry(30.8f, "Food"));

        PieDataSet set = new PieDataSet(entries, "");

        set.setColors(new int[]{R.color.green, R.color.yellow, R.color.red, R.color.blue}, context);
        set.setValueTextSize(0.1f);

        PieData data = new PieData(set);

        Description description = new Description();
        description.setEnabled(false);

        CharSequence title = "EXPENSES";

        holder.pieChart.setData(data);
        holder.pieChart.setDescription(description);
        holder.pieChart.setCenterText(title);
        holder.pieChart.notifyDataSetChanged();
        holder.pieChart.invalidate(); // refresh

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

        XAxis xAxis = holder.barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(8f);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(-45);

        YAxis right = holder.barChart.getAxisRight();
        right.setDrawLabels(false); // no axis labels
        right.setDrawAxisLine(false); // no axis line
        right.setDrawGridLines(false); // no grid lines
        right.setDrawZeroLine(true);

        YAxis left = holder.barChart.getAxisLeft();
        left.setDrawLabels(false); // no axis labels
        left.setDrawAxisLine(false); // no axis line
        left.setDrawGridLines(false); // no grid lines
        left.setDrawZeroLine(true);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.9f); // set custom bar width
        holder.barChart.setData(barData);
        holder.barChart.setFitBars(true); // make the x-axis fit exactly all bars
        holder.barChart.setDrawGridBackground(false);
        holder.barChart.setDescription(description);
        holder.barChart.animateXY(2000, 2000);
        holder.barChart.setScaleEnabled(true);
        holder.barChart.setDrawValueAboveBar(true);
        holder.barChart.setDrawGridBackground(false);
        holder.barChart.setDoubleTapToZoomEnabled(true);
        holder.barChart.setPinchZoom(true);
        barDataSet.setColor(Color.parseColor("#B71C1C"));
        holder.barChart.invalidate(); // refresh
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        PieChart pieChart;
        BarChart barChart;
        RadioGroup radioGroup;
        RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            barChart = itemView.findViewById(R.id.barChart);
            pieChart = itemView.findViewById(R.id.pieChart);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            int radioId = radioGroup.getCheckedRadioButtonId();
            radioButton = itemView.findViewById(radioId);
        }
    }

}
