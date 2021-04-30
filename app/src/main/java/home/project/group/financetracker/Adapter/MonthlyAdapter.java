package home.project.group.financetracker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Month;
import java.util.List;

import home.project.group.financetracker.EntityClass.ExpenseTransactionModel;
import home.project.group.financetracker.R;

public class MonthlyAdapter extends RecyclerView.Adapter<MonthlyAdapter.ViewHolder> {

    Context context;
    List<ExpenseTransactionModel> expenseList;
    List<List<Double>> monthly;

    public MonthlyAdapter(Context context, List<ExpenseTransactionModel> expenseList, List<List<Double>> monthly) {
        this.context = context;
        this.expenseList = expenseList;
        this.monthly = monthly;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.monthly_cards, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.month.setText(Month.of(position + 1).toString());
        holder.expenseTotal.setText(String.valueOf(monthly.get(position).get(0)));
        holder.revenueTotal.setText("$0");


       /*if (expenseList.get(position).getDate().getMonth() == 1 && expenseList.size() != 0 && !expenseList.isEmpty()) {
            holder.month.setText("Jan");
            holder.expenseTotal.setText(String.valueOf(monthly.get(position).get(0)));
            holder.revenueTotal.setText("$1000");
        } else if (expenseList.get(position).getDate().getMonth() == 2 && expenseList.size() != 0 && !expenseList.isEmpty()) {
            holder.month.setText("Feb");
            holder.expenseTotal.setText(String.valueOf(monthly.get(position).get(0)));
            holder.revenueTotal.setText("$1000");
        } else if (expenseList.get(position).getDate().getMonth() == 3 && expenseList.size() != 0 && !expenseList.isEmpty()) {
            holder.month.setText("Mar");
            holder.expenseTotal.setText(String.valueOf(monthly.get(position).get(0)));
            holder.revenueTotal.setText("$1000");
        } else if (expenseList.get(position).getDate().getMonth() == 4 && expenseList.size() != 0 && !expenseList.isEmpty()) {
            holder.month.setText("Apr");
            holder.expenseTotal.setText(String.valueOf(monthly.get(position).get(0)));
            holder.revenueTotal.setText("$1000");
        } else if (expenseList.get(position).getDate().getMonth() == 5 && expenseList.size() != 0 && !expenseList.isEmpty()) {
            holder.month.setText("May");
            holder.expenseTotal.setText(String.valueOf(monthly.get(position).get(0)));
            holder.revenueTotal.setText("$1000");
        } else if (expenseList.get(position).getDate().getMonth() == 6 && expenseList.size() != 0 && !expenseList.isEmpty()) {
            holder.month.setText("Jun");
            holder.expenseTotal.setText(String.valueOf(monthly.get(position).get(0)));
            holder.revenueTotal.setText("$1000");
        } else if (expenseList.get(position).getDate().getMonth() == 7 && expenseList.size() != 0 && !expenseList.isEmpty()) {
            holder.month.setText("July");
            holder.expenseTotal.setText(String.valueOf(monthly.get(position).get(0)));
            holder.revenueTotal.setText("$1000");
        } else if (expenseList.get(position).getDate().getMonth() == 8 && expenseList.size() != 0 && !expenseList.isEmpty()) {
            holder.month.setText("Aug");
            holder.expenseTotal.setText(String.valueOf(monthly.get(position).get(0)));
            holder.revenueTotal.setText("$1000");
        } else if (expenseList.get(position).getDate().getMonth() == 9 && expenseList.size() != 0 && !expenseList.isEmpty()) {
            holder.month.setText("Sep");
            holder.expenseTotal.setText(String.valueOf(monthly.get(position).get(0)));
            holder.revenueTotal.setText("$1000");
        } else if (expenseList.get(position).getDate().getMonth() == 10 && expenseList.size() != 0 && !expenseList.isEmpty()) {
            holder.month.setText("Oct");
            holder.expenseTotal.setText(String.valueOf(monthly.get(position).get(0)));
            holder.revenueTotal.setText("$1000");
        } else if (expenseList.get(position).getDate().getMonth() == 11 && expenseList.size() != 0 && !expenseList.isEmpty()) {
            holder.month.setText("Nov");
            holder.expenseTotal.setText(String.valueOf(monthly.get(position).get(0)));
            holder.revenueTotal.setText("$1000");
        } else if (expenseList.get(position).getDate().getMonth() == 0 && expenseList.size() != 0 && !expenseList.isEmpty()) {
            holder.month.setText("Dec");
            holder.expenseTotal.setText(String.valueOf(monthly.get(position).get(0)));
            holder.revenueTotal.setText("$1000");
        }*/

    }

    @Override
    public int getItemCount() {
        return monthly.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView month, expenseTotal, revenueTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            month = itemView.findViewById(R.id.txtMonth);
            expenseTotal = itemView.findViewById(R.id.txtExpenseTotal);
            revenueTotal = itemView.findViewById(R.id.txtRevenueTotal);
        }
    }

}
