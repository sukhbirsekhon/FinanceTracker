package home.project.group.financetracker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import home.project.group.financetracker.EntityClass.ExpenseTransactionModel;
import home.project.group.financetracker.R;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    Context context;
    List<ExpenseTransactionModel> list;

    public ExpenseAdapter(Context context, List<ExpenseTransactionModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.expenseName.setText(list.get(position).getExpenseName());
        holder.amount.setText(list.get(position).getAmount());
        holder.category.setText(list.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView expenseName, amount, category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            expenseName = itemView.findViewById(R.id.txtExpenseName);
            amount = itemView.findViewById(R.id.txtAmount);
            category = itemView.findViewById(R.id.txtCategory);

        }
    }

}
