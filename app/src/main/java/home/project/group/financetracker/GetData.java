package home.project.group.financetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import home.project.group.financetracker.Adapter.ExpenseAdapter;
import home.project.group.financetracker.EntityClass.ExpenseTransactionModel;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class GetData extends AppCompatActivity {

    RecyclerView recyclerView;

    private List<ExpenseTransactionModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);
        recyclerView = findViewById(R.id.recyclerview);

        getData();
    }

    private void getData() {
        list = new ArrayList<>();
        list = DatabaseClass.getDatabase(getApplicationContext()).getDao().getAllExpenseData();
        recyclerView.setAdapter(new ExpenseAdapter(getApplicationContext(), list, new ExpenseAdapter.DeleteItemClickListener() {
            @Override
            public void onItemDelete(int position, int id) {
                DatabaseClass.getDatabase(getApplicationContext()).getDao().deleteExpenseData(id);
                getData();
            }
        }));
    }
}