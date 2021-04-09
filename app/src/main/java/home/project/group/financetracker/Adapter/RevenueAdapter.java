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
import java.util.List;

import home.project.group.financetracker.EntityClass.RevenueTransactionModel;
import home.project.group.financetracker.R;

public class RevenueAdapter extends RecyclerView.Adapter<RevenueAdapter.ViewHolder> implements Filterable {

    Context context;
    List<RevenueTransactionModel> list;
    List<RevenueTransactionModel> allData;
    DeleteItemClickListener deleteItemClickListener;

    public RevenueAdapter(Context context, List<RevenueTransactionModel> list, DeleteItemClickListener deleteItemClickListener) {
        this.context = context;
        this.list = list;
        allData = new ArrayList<>(list);
        this.deleteItemClickListener = deleteItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_cards, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.expenseName.setText(list.get(position).getRevenueName());
        holder.amount.setText("$"+list.get(position).getAmount());
        holder.amount.setTextColor(Color.GREEN);
        holder.category.setText(list.get(position).getCategory());

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
            List<RevenueTransactionModel> filteredList = new ArrayList<>();
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

                for (RevenueTransactionModel item : allData) {
                    if (item.getRevenueName().toLowerCase().contains(filterPattern)) {
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

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView expenseName, amount, category;
        Button deleteId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            expenseName = itemView.findViewById(R.id.txtName);
            amount = itemView.findViewById(R.id.txtAmount);
            category = itemView.findViewById(R.id.txtCategory);
            deleteId = itemView.findViewById(R.id.deleteId);
        }
    }

}
