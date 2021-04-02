package home.project.group.financetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import home.project.group.financetracker.Adapter.ExpenseAdapter;
import home.project.group.financetracker.EntityClass.ExpenseTransactionModel;

import android.os.Bundle;

public class GetData extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);
        recyclerView = findViewById(R.id.recyclerview);

        getData();
    }

    private void getData() {
        recyclerView.setAdapter(new ExpenseAdapter(getApplicationContext(), DatabaseClass.getDatabase(getApplicationContext()).getDao().getAllExpenseData()));
    }
}