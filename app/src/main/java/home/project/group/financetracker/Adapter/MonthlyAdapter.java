package home.project.group.financetracker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import home.project.group.financetracker.R;

public class MonthlyAdapter extends RecyclerView.Adapter<MonthlyAdapter.ViewHolder> {

    Context context;

    public MonthlyAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.monthly_cards, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.month.setText("April");
        holder.expenseTotal.setText("$100.10");
        holder.revenueTotal.setText("$1000");

    }

    @Override
    public int getItemCount() {
        return 20;
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
