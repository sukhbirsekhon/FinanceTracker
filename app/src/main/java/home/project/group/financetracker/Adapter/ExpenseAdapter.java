package home.project.group.financetracker.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import home.project.group.financetracker.EntityClass.ExpenseTransactionModel;
import home.project.group.financetracker.R;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> implements Filterable {

    Context context;
    List<ExpenseTransactionModel> list;
    List<ExpenseTransactionModel> allData;
    DeleteItemClickListener deleteItemClickListener;
    UpdateClickListener btnClickListener;
    final String EXPENSE = "expense";

    public ExpenseAdapter(Context context, List<ExpenseTransactionModel> list, DeleteItemClickListener deleteItemClickListener, UpdateClickListener btnClickListener) {
        this.context = context;
        this.list = list;
        allData = new ArrayList<>(list);
        this.deleteItemClickListener = deleteItemClickListener;
        this.btnClickListener = btnClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_cards, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.expenseName.setText(list.get(position).getExpenseName());
        holder.amount.setText("$"+list.get(position).getAmount());
        holder.amount.setTextColor(Color.RED);
        holder.category.setText(list.get(position).getCategory());

        holder.update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String expenseName = list.get(position).getExpenseName();
                String amount = String.valueOf(list.get(position).getAmount());
                String note = list.get(position).getNote();
                Date dateObject = list.get(position).getDate();
                String date = dateObject.getMonth() + "-" + dateObject.getDay() + "-" + dateObject.getYear();
                String category = list.get(position).getCategory();
                btnClickListener.onBtnClick(EXPENSE, expenseName, amount, note, date, category);
            }
        });

        holder.deleteId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItemClickListener.onItemDelete(position, list.get(position).getKey());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ExpenseTransactionModel> filteredList = new ArrayList<>();
            /**
             * If user doesn't provide any text then sends back original list with existing data
             */
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(allData);
            }
            /**
             * If user provides some text then use that text to compare it against transaction name
             * in the original list
             */
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (ExpenseTransactionModel item : allData) {
                    if (item.getExpenseName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            /**
             * Clear the old list and display filtered list
             */
            list.clear();
            list.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public interface DeleteItemClickListener {
        void onItemDelete(int position, int id);
    }

    public interface UpdateClickListener {
        void onBtnClick(String type, String name, String amount, String note, String date, String category);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView expenseName, amount, category;
        Button deleteId, update;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            expenseName = itemView.findViewById(R.id.txtName);
            amount = itemView.findViewById(R.id.txtAmount);
            category = itemView.findViewById(R.id.txtCategory);
            deleteId = itemView.findViewById(R.id.deleteId);
            update = itemView.findViewById(R.id.btnUpdate);
        }
    }

}
